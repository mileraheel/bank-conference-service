package com.bank.meetingservice.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum ResponseStatus {
    SUCCESS,
    FAIL,
    ERROR;

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}
