package com.bank.meetingservice.dto.request;

import com.bank.meetingservice.entity.Room;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MeetingRequestDto {

    @NotBlank(message = "meeting title cannot be empty or null")
    @Pattern(regexp = "^[^<>{}\"/|;:.,~!?@#$%^=&*\\]\\\\()\\[¿§«»ω⊙¤°℃℉€¥£¢¡®©_+]*$",
            message = "please do not enter any special characters in meeting title")
    private String title;

    @NotNull(message = "please provide number of people for the meeting")
    private Integer people;

    private Room room;

    @NotNull
    String startTime;
    @NotNull
    String endTime;
}
