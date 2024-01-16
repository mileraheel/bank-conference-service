package com.bank.meetingservice.controllers;

import com.bank.meetingservice.AbstractTestApplication;
import com.bank.meetingservice.entity.MaintenanceWindow;
import com.bank.meetingservice.entity.Room;
import com.bank.meetingservice.enums.ResponseErrorCode;
import com.bank.meetingservice.enums.ResponseStatus;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeetingsControllerTest extends AbstractTestApplication {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US);

    @Before
    public void init() {
        Room room1 = Room.builder().name("Amaze").capacity(3).build();
        Room room2 = Room.builder().name("Beauty").capacity(7).build();
        Room room3 = Room.builder().name("Inspire").capacity(12).build();
        Room room4 = Room.builder().name("Strive").capacity(20).build();
        roomRepository.saveAll(List.of(room1, room2, room3, room4));

        MaintenanceWindow maintenanceWindow1 = MaintenanceWindow.builder().fromTime(LocalTime.parse("09:00", formatter)).toTime(LocalTime.parse("09:15", formatter)).build();
        MaintenanceWindow maintenanceWindow2 = MaintenanceWindow.builder().fromTime(LocalTime.parse("13:00", formatter)).toTime(LocalTime.parse("13:15", formatter)).build();
        MaintenanceWindow maintenanceWindow3 = MaintenanceWindow.builder().fromTime(LocalTime.parse("17:00", formatter)).toTime(LocalTime.parse("17:15", formatter)).build();
        maintenanceWindowRepository.saveAll(List.of(maintenanceWindow1, maintenanceWindow2, maintenanceWindow3));
    }

    @Test
    public void createMeetingTestSuccess() throws Exception {
        JSONObject requestDto = new JSONObject();
        requestDto.put("title", "Meeting Request 1").put("people", 5).put("startTime","10:00").put("endTime","10:15");
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/meetings").content(requestDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        JSONObject res = new JSONObject(response.getContentAsString());
        JSONObject data = new JSONObject(res.get("data").toString());
        Assert.assertEquals(res.get("status"), ResponseStatus.SUCCESS.toValue());
        Assert.assertEquals(data.get("room"), "Beauty");
    }

    @Test
    public void createMeetingTestMeetingStartTimeNotInterval() throws Exception {
        JSONObject requestDto = new JSONObject();
        requestDto.put("title", "Meeting Request 1").put("people", 5).put("startTime","10:10").put("endTime","10:15");
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/meetings").content(requestDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        JSONObject res = new JSONObject(response.getContentAsString());
        Assert.assertEquals(res.get("status"), ResponseStatus.ERROR.toValue());
        Assert.assertTrue(res.get("errorCode").toString().contains(ResponseErrorCode.MEETING_START_TIME_NOT_INTERVAL.getCode()));
    }

    @Test
    public void createMeetingTestMeetingEndTimeNotInterval() throws Exception {
        JSONObject requestDto = new JSONObject();
        requestDto.put("title", "Meeting Request 1").put("people", 5).put("startTime","10:00").put("endTime","10:10");
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/meetings").content(requestDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        JSONObject res = new JSONObject(response.getContentAsString());
        Assert.assertEquals(res.get("status"), ResponseStatus.ERROR.toValue());
        Assert.assertTrue(res.get("errorCode").toString().contains(ResponseErrorCode.MEETING_END_TIME_NOT_INTERVAL.getCode()));
    }

    @Test
    public void createMeetingTestMeetingStartEndTimeNotValid() throws Exception {
        JSONObject requestDto = new JSONObject();
        requestDto.put("title", "Meeting Request 1").put("people", 5).put("startTime","ABCDEF").put("endTime","10:10");
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/meetings").content(requestDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        JSONObject res = new JSONObject(response.getContentAsString());
        Assert.assertEquals(res.get("status"), ResponseStatus.ERROR.toValue());
        Assert.assertTrue(res.get("errorCode").toString().contains(ResponseErrorCode.MEETING_START_END_TIME_NOT_VALID.getCode()));
    }

    @Test
    public void createMeetingTestMeetingEndTimeBeforeStartTime() throws Exception {
        JSONObject requestDto = new JSONObject();
        requestDto.put("title", "Meeting Request 1").put("people", 5).put("startTime","10:00").put("endTime","09:45");
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/meetings").content(requestDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        JSONObject res = new JSONObject(response.getContentAsString());
        Assert.assertEquals(res.get("status"), ResponseStatus.ERROR.toValue());
        Assert.assertTrue(res.get("errorCode").toString().contains(ResponseErrorCode.MEETING_END_TIME_BEFORE_START_TIME.getCode()));
    }

    @Test
    public void createMeetingTestMeetingPersonGreatThan1() throws Exception {
        JSONObject requestDto = new JSONObject();
        requestDto.put("title", "Meeting Request 1").put("people", 1).put("startTime","10:00").put("endTime","10:45");
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/meetings").content(requestDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        JSONObject res = new JSONObject(response.getContentAsString());
        Assert.assertEquals(res.get("status"), ResponseStatus.ERROR.toValue());
        Assert.assertTrue(res.get("errorCode").toString().contains(ResponseErrorCode.MEETING_PERSON_GREATER_THAN_1.getCode()));
    }

    @Test
    public void createMeetingTestMaintenanceWindowStartingInMeeting() throws Exception {
        JSONObject requestDto = new JSONObject();
        requestDto.put("title", "Meeting Request 1").put("people", 1).put("startTime","08:45").put("endTime","09:15");
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/meetings").content(requestDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        JSONObject res = new JSONObject(response.getContentAsString());
        Assert.assertEquals(res.get("status"), ResponseStatus.ERROR.toValue());
        Assert.assertTrue(res.get("errorCode").toString().contains(ResponseErrorCode.MAINTENANCE_WINDOW_STARTING_IN_MEETING.getCode()));
    }

    @Test
    public void createMeetingTestNoMeetingRoomAvailable() throws Exception {
        JSONObject requestDto = new JSONObject();
        requestDto.put("title", "Meeting Request 1").put("people", 25).put("startTime","08:00").put("endTime","08:15");
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/meetings").content(requestDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        JSONObject res = new JSONObject(response.getContentAsString());
        Assert.assertEquals(res.get("status"), ResponseStatus.ERROR.toValue());
        Assert.assertTrue(res.get("errorCode").toString().contains(ResponseErrorCode.NO_MEETING_ROOM_AVAILABLE.getCode()));
    }
}
