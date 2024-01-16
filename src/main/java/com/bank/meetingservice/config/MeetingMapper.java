package com.bank.meetingservice.config;

import com.bank.meetingservice.dto.request.MeetingRequestDto;
import com.bank.meetingservice.dto.response.MeetingsResponseDto;
import com.bank.meetingservice.entity.Meeting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",imports = {ArrayList.class})
public interface MeetingMapper {

    @Mappings({
            @Mapping(target = "title", source = "title")
    })
    Meeting meetingRequestDtoToMeeting(MeetingRequestDto meetingRequestDto);
    @Mappings({
            @Mapping(target = "room", source = "meeting.room.name"),
            @Mapping(target = "startTime", source = "meetingStartTime"),
            @Mapping(target = "endTime", source = "meetingEndTime")

    })
    MeetingsResponseDto meetingToMeetingResponseDto(Meeting meeting);

    @Mappings({
            @Mapping(target = "room", source = "meeting.room.name"),
            @Mapping(target = "startTime", source = "meetingStartTime"),
            @Mapping(target = "endTime", source = "meetingEndTime")

    })
    List<MeetingsResponseDto> meetingToMeetingResponseDtoList(List<Meeting> meeting);
}
