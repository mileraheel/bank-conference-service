package com.bank.meetingservice.business.impl.v1;

import com.bank.meetingservice.business.impl.AbstractBusinessService;
import com.bank.meetingservice.config.MeetingMapper;
import com.bank.meetingservice.dto.common.ServiceTransferDto;
import com.bank.meetingservice.dto.response.MeetingsResponseDto;
import com.bank.meetingservice.repository.MeetingRepository;
import lombok.CustomLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CustomLog
public class GetAllMeetingsService extends AbstractBusinessService<Object, List<MeetingsResponseDto>, ServiceTransferDto> {

    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;

    public GetAllMeetingsService(MeetingRepository meetingRepository, MeetingMapper meetingMapper) {
        this.meetingRepository = meetingRepository;
        this.meetingMapper = meetingMapper;
    }

    @Override
    protected ServiceTransferDto getTransferDto() {
        return null;
    }

    @Override
    protected String getServiceName() {
        return "GetAllMeetingsService";
    }

    @Override
    protected void validateRequest(Object request, ServiceTransferDto transferDto) {

    }

    @Override
    protected List<MeetingsResponseDto> execute(Object request, ServiceTransferDto transferDto) {
        return meetingMapper.meetingToMeetingResponseDtoList(meetingRepository.findAll());
    }
}
