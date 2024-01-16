package com.bank.meetingservice.business.impl.v1;

import com.bank.meetingservice.business.impl.AbstractBusinessService;
import com.bank.meetingservice.config.MeetingMapper;
import com.bank.meetingservice.dto.common.ErrorDetail;
import com.bank.meetingservice.dto.common.ServiceTransferDto;
import com.bank.meetingservice.dto.request.MeetingRequestDto;
import com.bank.meetingservice.dto.response.MeetingsResponseDto;
import com.bank.meetingservice.entity.Meeting;
import com.bank.meetingservice.enums.ResponseErrorCode;
import com.bank.meetingservice.exception.MeetingServiceException;
import com.bank.meetingservice.repository.MaintenanceWindowRepository;
import com.bank.meetingservice.repository.MeetingRepository;
import com.bank.meetingservice.repository.RoomRepository;
import com.bank.meetingservice.validators.MaintenanceWindowValidator;
import com.bank.meetingservice.validators.TimeValidator;
import lombok.CustomLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

@Service
@CustomLog
public class CreateMeetingService extends AbstractBusinessService<MeetingRequestDto, MeetingsResponseDto, ServiceTransferDto> {

    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;
    private final MaintenanceWindowRepository maintenanceWindowRepository;
    private final RoomRepository roomRepository;

    private final TimeValidator timeValidator;
    private final MaintenanceWindowValidator maintenanceWindowValidator;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US);

    public CreateMeetingService(MeetingRepository meetingRepository, MeetingMapper meetingMapper, @Value("${meeting-interval}")
        int meetingInterval, MaintenanceWindowRepository maintenanceWindowRepository, RoomRepository roomRepository, TimeValidator timeValidator, MaintenanceWindowValidator maintenanceWindowValidator) {
        this.meetingRepository = meetingRepository;
        this.meetingMapper = meetingMapper;
        this.meetingInterval = meetingInterval;
        this.maintenanceWindowRepository = maintenanceWindowRepository;
        this.roomRepository = roomRepository;
        this.timeValidator = timeValidator;
        this.maintenanceWindowValidator = maintenanceWindowValidator;
    }

    private int meetingInterval;

    @Override
    protected ServiceTransferDto getTransferDto() {
        return ServiceTransferDto.builder().errorDetails(new ArrayList<>()).build();
    }

    @Override
    protected String getServiceName() {
        return "CreateMeetingRequestService";
    }

    @Override
    protected void validateRequest(MeetingRequestDto request, ServiceTransferDto transferDto) {

        transferDto.setFromTime(request.getStartTime());
        transferDto.setToTime(request.getEndTime());
        timeValidator.validateTime(transferDto);
        maintenanceWindowValidator.validateMaintenanceWindow(transferDto);

        if(request.getPeople()<2) {
           transferDto.getErrorDetails().add(new ErrorDetail(ResponseErrorCode.MEETING_PERSON_GREATER_THAN_1)) ;
        }

        if(!transferDto.getErrorDetails().isEmpty()) {
            throw new MeetingServiceException(transferDto.getErrorDetails());
        }
    }

    @Override
    protected MeetingsResponseDto execute(MeetingRequestDto request, ServiceTransferDto transferDto) {
        AtomicReference<MeetingsResponseDto> response = new AtomicReference<>();
        roomRepository.findAllByCapacityGreaterThanEqualOrderByCapacityAsc(request.getPeople()).stream()
                .filter(room -> meetingRepository.checkIfRoomBookedInGivenTime(transferDto.getMeetingStartTime(), transferDto.getMeetingEndTIme(), room).isEmpty())
                .findFirst()
                .ifPresentOrElse(room ->
                        response.set(meetingMapper.meetingToMeetingResponseDto(meetingRepository.saveAndFlush(Meeting.builder().meetingStartTime(transferDto.getMeetingStartTime()).meetingEndTime(transferDto.getMeetingEndTIme())
                        .people(request.getPeople()).title(request.getTitle()).room(room).build()))),
                        () -> {throw new MeetingServiceException(ResponseErrorCode.NO_MEETING_ROOM_AVAILABLE);});
        return response.get();
    }
}
