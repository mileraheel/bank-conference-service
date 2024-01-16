package com.bank.meetingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class RoomResponseDto {
    String title;
    int capacity;
}
