package com.bank.meetingservice.business.impl;


import com.bank.meetingservice.business.BusinessService;
import com.bank.meetingservice.dto.common.ErrorDetail;
import com.bank.meetingservice.enums.ResponseErrorCode;
import com.bank.meetingservice.exception.MeetingServiceException;
import lombok.CustomLog;
import org.springframework.core.io.ByteArrayResource;

import java.text.MessageFormat;
import java.util.List;

@CustomLog
@SuppressWarnings({"squid:S1172","squid:S4977"})
public abstract class AbstractBusinessService<I,O,T> implements BusinessService<I,O> {

    private static final String SERVICE_RETURNED_RESPONSE = "Service: {} returned response: {}";
    private static final String SERVICE_CALLED_BY_REQUEST = "Service: {} called by request: {}";

    @Override
    public final O doAction(I request) {

        T transferDto=getTransferDto();

        String serviceName = getServiceName();

        long startTime = System.currentTimeMillis();

        logRequestOrResponse(request, SERVICE_CALLED_BY_REQUEST);


        try {
            //1. validate request
            validateRequest(request,transferDto);
            //2. execute service
            O response = execute(request,transferDto);

            logRequestOrResponse(response, SERVICE_RETURNED_RESPONSE);
            //4. post execution
            response = postExecution(request,transferDto, response);
            log.info("Service: {} took : {} Millisecond", serviceName, System.currentTimeMillis() - startTime);
            return response;
        } catch (MeetingServiceException exception) {
            return handleFailure(exception, request);
        } catch (Throwable exception) {
            log.error("UnHandled Exception Occurred.", exception);
            MeetingServiceException meetingServiceException =
                    new MeetingServiceException(List.of(new ErrorDetail(ResponseErrorCode.UNEXPECTED_ERROR)));
            return handleFailure(meetingServiceException, request);
        }finally {
            handleFinallyBlock(request);
        }
    }

    protected abstract T getTransferDto();

    protected abstract String getServiceName();

    protected abstract void validateRequest(I request,T transferDto);

    protected abstract O execute(I request,T transferDto);

    protected O handleFailure(MeetingServiceException exp, I request) {

        log.error(MessageFormat.format("Error: {0} happened in service {1}", exp.getMessage(), getServiceName()),
                exp);
        throw exp;
    }

    protected O postExecution(I request,T transferDto, O response) {
        return response;
    }

    protected <T> void logRequestOrResponse(T t, String type) {
        try {
            if (t instanceof ByteArrayResource) {
                return;
            }
            log.info(type, getServiceName(), t);
        } catch (Exception ex) {
            log.error("Exception in processing log for response::", ex);
        }
    }

    protected void handleFinallyBlock(I request) {
        // Handle Add Finally Block
    }
}
