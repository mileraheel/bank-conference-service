package com.bank.meetingservice.enums;

import lombok.Getter;

@Getter
public enum ResponseErrorCode {

    // Global Errors
    BAD_REQUEST_ERROR("5002", "Input Data Invalid"),
    FEIGN_SERVICE_ERROR("5001", "Backend Service Call Error"),
    UNEXPECTED_ERROR("500", "Internal Server Error"),
    INVALID_REQUEST("5004", "Invalid Request"),
    INVALID_DATA("5005", "Invalid Data"),
    AUTH_TOKEN_REQ_HTTP_ERROR("5006", "Exception while Authentication Token Req"),
    MEETING_START_TIME_NOT_INTERVAL("M001", "Meeting start time is not in the interval"),
    MEETING_END_TIME_NOT_INTERVAL("M002", "Meeting end time is not in the interval"),
    MEETING_START_END_TIME_NOT_VALID("M003", "meeting start / end time not in proper 24 hour format, eg : 13:00"),
    MEETING_END_TIME_BEFORE_START_TIME("M004", "meeting end time cannot be before start time"),

    MEETING_PERSON_GREATER_THAN_1("M005", "Alone person cannot be in a meeting"),
    MAINTENANCE_WINDOW_STARTING_IN_MEETING("W001", "Maintenance windows starting in meeting time, kindly prepone"),
    /*MAINTENANCE_WINDOW_ENDING_IN_MEETING("W002", "Maintenance windows ending in meeting time, kindly postpone"),*/

    NO_MEETING_ROOM_AVAILABLE("M006", "No Meeting room available for the requested time"),
    ;
    private final String code;
    private final String message;

    ResponseErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
