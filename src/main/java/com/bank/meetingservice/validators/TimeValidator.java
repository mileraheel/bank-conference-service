package com.bank.meetingservice.validators;

import com.bank.meetingservice.dto.common.ErrorDetail;
import com.bank.meetingservice.dto.common.ServiceTransferDto;
import com.bank.meetingservice.enums.ResponseErrorCode;
import com.bank.meetingservice.exception.MeetingServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class TimeValidator {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US);

    @Value("${meeting-interval}")
    private int meetingInterval;

    public void validateTime(ServiceTransferDto serviceTransferDto) {

        LocalTime meetingStartTime ;
        LocalTime meetingEndTime;
        try {
            meetingStartTime = LocalTime.parse(serviceTransferDto.getFromTime(), formatter);
            meetingEndTime = LocalTime.parse(serviceTransferDto.getToTime(), formatter);

        }catch (Exception e) {
            throw new MeetingServiceException(ResponseErrorCode.MEETING_START_END_TIME_NOT_VALID);
        }
        serviceTransferDto.setMeetingStartTime(meetingStartTime);
        serviceTransferDto.setMeetingEndTIme(meetingEndTime);
        if(Integer.parseInt(serviceTransferDto.getFromTime().split(":")[1]) % meetingInterval != 0) {
            serviceTransferDto.getErrorDetails().add(new ErrorDetail(ResponseErrorCode.MEETING_START_TIME_NOT_INTERVAL));
        }
        if(Integer.parseInt(serviceTransferDto.getToTime().split(":")[1]) % meetingInterval != 0) {
            serviceTransferDto.getErrorDetails().add(new ErrorDetail(ResponseErrorCode.MEETING_END_TIME_NOT_INTERVAL));
        }
        if (!meetingEndTime.isAfter(meetingStartTime)) {
            serviceTransferDto.getErrorDetails().add(new ErrorDetail(ResponseErrorCode.MEETING_END_TIME_BEFORE_START_TIME));
        }


    }
}
