package com.example.roombooking.service;

import com.example.roombooking.model.Booking;
import com.example.roombooking.model.Room;
import com.example.roombooking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

	private final BookingRepository bookingRepository;

	public BookingService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	// Check if the booking overlaps with any existing bookings for the same room
	public boolean isRoomAvailable(Room room, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
		List<Booking> bookings = bookingRepository.findByRoom(room);

		for (Booking existingBooking : bookings) {
			// Check for overlapping bookings
			if (fromDateTime.isBefore(existingBooking.getToDateTime())
					&& toDateTime.isAfter(existingBooking.getFromDateTime())) {
				return false; // Room is not available
			}
		}
		return true; // Room is available
	}

	public Booking saveBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}
}