package com.bank.meetingservice.controllers;

import com.bank.meetingservice.AbstractTestApplication;
import com.bank.meetingservice.entity.MaintenanceWindow;
import com.bank.meetingservice.entity.Room;
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
public class RoomsControllerTest extends AbstractTestApplication {
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
    public void getAllRoomsAvailableTest() throws Exception{
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/rooms").param("fromTime","10:00").param("toTime","10:30")
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        JSONObject res = new JSONObject(response.getContentAsString());
        JSONObject data = new JSONObject(res.get("data").toString());
        Assert.assertEquals(res.get("status"), ResponseStatus.SUCCESS.toValue());
    }
}
