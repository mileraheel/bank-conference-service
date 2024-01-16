package com.bank.meetingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class AvailableRoomsResponse {
    List<RoomResponseDto> rooms;
}
