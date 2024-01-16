package com.bank.meetingservice.controllers;

import com.bank.meetingservice.dto.common.Response;
import com.bank.meetingservice.business.impl.v1.GetAllAvailableRoomsService;
import com.bank.meetingservice.dto.request.AvailableRoomsRequest;
import com.bank.meetingservice.dto.response.AvailableRoomsResponse;
import com.bank.meetingservice.enums.ResponseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rooms")
@AllArgsConstructor
public class RoomsController {

    private GetAllAvailableRoomsService getAllAvailableRoomsService;

    @GetMapping()
    public ResponseEntity<Response<AvailableRoomsResponse>> getAllRoomsAvailable(
            @RequestParam @NotNull(message = "please provide expected meeting start time") String fromTime,
            @RequestParam @NotNull(message = "please provide expected meeting end time") String toTime) {

        return ResponseEntity.ok().body(Response.<AvailableRoomsResponse>builder()
                .data(getAllAvailableRoomsService.doAction(AvailableRoomsRequest.builder().startTime(fromTime).endTime(toTime).build()))
                .status(ResponseStatus.SUCCESS)
                .build());
    }
}
