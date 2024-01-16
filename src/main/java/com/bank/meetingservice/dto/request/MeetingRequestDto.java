package com.bank.meetingservice.dto.request;

import com.bank.meetingservice.entity.Room;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MeetingRequestDto {

    @NotNull(message = "meeting title cannot be blank")
    private String title;

    @NotNull
    private Integer people;

    private Room room;

    @NotNull
    String startTime;
    @NotNull
    String endTime;
}
