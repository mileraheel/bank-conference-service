package com.bank.meetingservice.business.impl.v1;

import com.bank.meetingservice.business.impl.AbstractBusinessService;
import com.bank.meetingservice.dto.common.ServiceTransferDto;
import com.bank.meetingservice.dto.request.AvailableRoomsRequest;
import com.bank.meetingservice.dto.response.AvailableRoomsResponse;
import com.bank.meetingservice.dto.response.RoomResponseDto;
import com.bank.meetingservice.enums.ResponseErrorCode;
import com.bank.meetingservice.exception.MeetingServiceException;
import com.bank.meetingservice.repository.MeetingRepository;
import com.bank.meetingservice.repository.RoomRepository;
import com.bank.meetingservice.validators.MaintenanceWindowValidator;
import com.bank.meetingservice.validators.TimeValidator;
import lombok.CustomLog;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@CustomLog
@Service
public class GetAllAvailableRoomsService extends AbstractBusinessService<AvailableRoomsRequest, AvailableRoomsResponse, ServiceTransferDto> {

    private final MeetingRepository meetingRepository;
    private final RoomRepository roomRepository;

    private final MaintenanceWindowValidator maintenanceWindowValidator;
    private final TimeValidator timeValidator;

    public GetAllAvailableRoomsService(MeetingRepository meetingRepository, RoomRepository roomRepository, MaintenanceWindowValidator maintenanceWindowValidator, TimeValidator timeValidator) {
        this.meetingRepository = meetingRepository;
        this.roomRepository = roomRepository;
        this.maintenanceWindowValidator = maintenanceWindowValidator;
        this.timeValidator = timeValidator;
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US);
    @Override
    protected ServiceTransferDto getTransferDto() {
        return new ServiceTransferDto();
    }

    @Override
    protected String getServiceName() {
        return "GetAllAvailableRoomsService";
    }

    @Override
    protected void validateRequest(AvailableRoomsRequest request, ServiceTransferDto transferDto) {
        transferDto.setFromTime(request.getStartTime());
        transferDto.setToTime(request.getEndTime());
        timeValidator.validateTime(transferDto);
        maintenanceWindowValidator.validateMaintenanceWindow(transferDto);

        if(!transferDto.getErrorDetails().isEmpty()) {
            throw new MeetingServiceException(transferDto.getErrorDetails());
        }
    }

    @Override
    protected AvailableRoomsResponse execute(AvailableRoomsRequest request, ServiceTransferDto transferDto) {
        List<RoomResponseDto> rooms = roomRepository.findAll().stream()
                .filter(room -> meetingRepository.checkIfRoomBookedInGivenTime(transferDto.getMeetingStartTime(), transferDto.getMeetingEndTIme(), room).isEmpty())
                .map(room -> RoomResponseDto.builder().capacity(room.getCapacity()).title(room.getName()).build())
                .collect(Collectors.toList());
        if(rooms.isEmpty()) {
            throw new MeetingServiceException(ResponseErrorCode.NO_MEETING_ROOM_AVAILABLE);
        } else {
            return AvailableRoomsResponse.builder().rooms(rooms).build();
        }
    }
}
