package com.example.roombooking.repository;

import com.example.roombooking.model.Booking;
import com.example.roombooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByRoomAndFromDateTimeLessThanEqualAndToDateTimeGreaterThanEqual(Room room, LocalDateTime from,
			LocalDateTime to);

	List<Booking> findByRoom(Room room);
}
