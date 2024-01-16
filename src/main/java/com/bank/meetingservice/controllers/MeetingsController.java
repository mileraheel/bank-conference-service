package com.bank.meetingservice.controllers;


import com.bank.meetingservice.dto.common.Response;
import com.bank.meetingservice.dto.request.MeetingRequestDto;
import com.bank.meetingservice.enums.ResponseStatus;
import com.bank.meetingservice.business.impl.v1.CreateMeetingService;
import com.bank.meetingservice.business.impl.v1.GetAllMeetingsService;
import com.bank.meetingservice.dto.response.MeetingsResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meetings")
@AllArgsConstructor
public class MeetingsController {

    private final CreateMeetingService createMeetingService;
    private final GetAllMeetingsService getAllMeetingsService;

    @GetMapping()
    public ResponseEntity<Response<List<MeetingsResponseDto>>> getAllMeetings() {

        return ResponseEntity.ok().body(Response.<List<MeetingsResponseDto>>builder()
                        .data(getAllMeetingsService.doAction(null))
                .status(com.bank.meetingservice.enums.ResponseStatus.SUCCESS)
                .build());
    }

    @PostMapping()
    public ResponseEntity<Response> createMeeting(@RequestBody @Valid MeetingRequestDto meetingRequestDto) {
        return ResponseEntity.ok().body(Response.<MeetingsResponseDto>builder()
                        .data(createMeetingService.doAction(meetingRequestDto))
                .status(ResponseStatus.SUCCESS)
                .build());
    }
}
