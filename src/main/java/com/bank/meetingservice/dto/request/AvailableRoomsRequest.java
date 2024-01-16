package com.bank.meetingservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AvailableRoomsRequest {
    @NotNull
    String startTime;
    @NotNull
    String endTime;
}
