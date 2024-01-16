package com.bank.meetingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@AllArgsConstructor
@Data
@Builder
public class MeetingsResponseDto {

    private String title;

    private Integer people;

    private String room;

    String startTime;
    String endTime;
}
