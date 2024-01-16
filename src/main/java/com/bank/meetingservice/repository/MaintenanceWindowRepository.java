package com.bank.meetingservice.repository;

import com.bank.meetingservice.entity.MaintenanceWindow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface MaintenanceWindowRepository extends JpaRepository<MaintenanceWindow, Long> {

    @Query("SELECT tct.id FROM MaintenanceWindow tct " +
            "WHERE (tct.fromTime >= :reqFromTime and tct.fromTime <= :reqToTime )" +
            "OR  (tct.toTime >= :reqFromTime and tct.toTime <= :reqToTime ) " )
    List<Long> checkIfMaintenanceWindowValidInGivenTime(@Param("reqFromTime") LocalTime fromTime ,
                                                        @Param("reqToTime") LocalTime toTime);
}
