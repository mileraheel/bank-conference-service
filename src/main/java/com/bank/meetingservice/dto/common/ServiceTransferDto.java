package com.bank.meetingservice.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTransferDto {
    List<ErrorDetail> errorDetails=new ArrayList<>();

    LocalTime meetingStartTime;
    LocalTime meetingEndTIme;

    String fromTime;
    String toTime;

    public String getErrorCodes() {
        return errorDetails.isEmpty() ? null : errorDetails.get(0).getErrorCode();
    }

    public String getErrorMessages() {
        return errorDetails.isEmpty() ? null : errorDetails.get(0).getErrorDescription();
    }


}
