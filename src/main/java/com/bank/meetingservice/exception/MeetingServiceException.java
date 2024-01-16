package com.bank.meetingservice.exception;

import com.bank.meetingservice.dto.common.ErrorDetail;
import com.bank.meetingservice.enums.ResponseErrorCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuppressWarnings("squid:S1165")
public class MeetingServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    List<ErrorDetail> errorDetails = new ArrayList<>();

    public MeetingServiceException(List<ErrorDetail> errorDetails) {
        this.errorDetails = errorDetails;
    }

    public MeetingServiceException(ResponseErrorCode responseErrorCode) {
       this.errorDetails = List.of(new ErrorDetail(responseErrorCode));

    }
}
