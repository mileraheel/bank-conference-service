package com.bank.meetingservice.repository;

import com.bank.meetingservice.entity.Meeting;
import com.bank.meetingservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Meeting saveAndFlush(Meeting meeting);

    @Query("SELECT tct.id FROM Meeting tct " +
            "WHERE tct.room = :room AND ((tct.meetingStartTime >= :reqFromTime and tct.meetingStartTime <= :reqToTime )" +
            "OR  (tct.meetingEndTime >= :reqFromTime and tct.meetingEndTime <= :reqToTime )) " )
    List<Long> checkIfRoomBookedInGivenTime(@Param("reqFromTime") LocalTime fromTime ,
                                      @Param("reqToTime") LocalTime toTime, @Param("room") Room room);

    List<Meeting> findAll();
}
