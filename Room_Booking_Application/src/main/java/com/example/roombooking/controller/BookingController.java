package com.example.roombooking.controller;

import com.example.roombooking.model.Booking;
import com.example.roombooking.model.Room;
import com.example.roombooking.service.BookingService;
import com.example.roombooking.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class BookingController {

	private final RoomService roomService;
	private final BookingService bookingService;

	public BookingController(RoomService roomService, BookingService bookingService) {
		this.roomService = roomService;
		this.bookingService = bookingService;
	}

	@GetMapping("/font")
	public String home1(Model model) {
		// Add any necessary data to the model
		return "home"; // Thymeleaf template name
	}

	@GetMapping("/calendar")
	public String home3(Model model) {
		// Add any necessary data to the model
		return "calendar"; // Thymeleaf template name
	}

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("rooms", roomService.getAllRooms());
		model.addAttribute("bookings", bookingService.getAllBookings());
		return "index";
	}

	@PostMapping("/book")
	public String bookRoom(@RequestParam Long roomId, @RequestParam String fromDateTime,
			@RequestParam String toDateTime, @RequestParam String purpose, Model model) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		LocalDateTime from = LocalDateTime.parse(fromDateTime, formatter);
		LocalDateTime to = LocalDateTime.parse(toDateTime, formatter);

		Room room = roomService.getAllRooms().stream().filter(r -> r.getId().equals(roomId)).findFirst().orElse(null);

		if (room != null && !bookingService.isRoomAvailable(room, from, to)) {
			model.addAttribute("error", "Room is already booked for the selected time.");
			model.addAttribute("rooms", roomService.getAllRooms());
			model.addAttribute("bookings", bookingService.getAllBookings());
			return "index";
		}

		Booking booking = new Booking();
		booking.setRoom(room);
		booking.setFromDateTime(from);
		booking.setToDateTime(to);
		booking.setPurpose(purpose);

		bookingService.saveBooking(booking);

		return "redirect:/";
	}

	@GetMapping("/api/bookings")
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> getBookings() {
		List<Booking> bookings = bookingService.getAllBookings();

		List<Map<String, Object>> events = bookings.stream().map(booking -> {
			Map<String, Object> event = Map.of("start", booking.getFromDateTime().toString(), "end",
					booking.getToDateTime().toString(), "backgroundColor", "red", "borderColor", "red", "textColor",
					"white");
			return event;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(events);
	}

	// redirected to the login page
	@GetMapping("/login")
	public String home2(Model model) {
		// Add any necessary data to the model
		return "login"; // Thymeleaf template name
	}

}
