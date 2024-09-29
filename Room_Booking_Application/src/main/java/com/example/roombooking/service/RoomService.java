package com.example.roombooking.service;

import com.example.roombooking.model.Room;
import com.example.roombooking.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

	private final RoomRepository roomRepository;

	public RoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

}
