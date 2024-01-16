package com.bank.meetingservice.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bank.meetingservice.dto.common.ErrorDetail;
import com.bank.meetingservice.dto.common.Response;
import com.bank.meetingservice.enums.ResponseStatus;
import lombok.CustomLog;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@CustomLog
public class RequestHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MeetingServiceException.class)
    public ResponseEntity<Response> handleWealthLocalEquityException(MeetingServiceException meetingServiceException) {
        log.error(meetingServiceException.getErrorDetails().toString());
        return new ResponseEntity<>(Response.builder().status(ResponseStatus.ERROR)
                .errorCode(meetingServiceException.errorDetails.stream().map(ErrorDetail::getErrorCode).collect(Collectors.joining(", ")))
                .message(meetingServiceException.errorDetails.stream().map(ErrorDetail::getErrorDescription).collect(Collectors.joining(", "))).build(),
                HttpStatus.OK);
    }
}
