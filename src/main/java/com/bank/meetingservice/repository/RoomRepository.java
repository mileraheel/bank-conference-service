package com.bank.meetingservice.repository;

import com.bank.meetingservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByCapacityGreaterThanEqualOrderByCapacityAsc(int people);
}
