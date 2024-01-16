package com.bank.meetingservice.dto.common;

import com.bank.meetingservice.enums.ResponseErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ErrorDetail {

    String errorCode;
    String errorDescription;

    public void setErrorCode(ResponseErrorCode responseErrorCode) {
        this.errorCode = responseErrorCode.getCode();
        this.errorDescription = responseErrorCode.getMessage();
    }

    public ErrorDetail(ResponseErrorCode responseErrorCode) {
        this.errorCode = responseErrorCode.getCode();
        this.errorDescription = responseErrorCode.getMessage();
    }
}