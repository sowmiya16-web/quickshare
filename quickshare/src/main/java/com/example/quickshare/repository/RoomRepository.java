package com.example.quickshare.repository;
import com.example.quickshare.repository.RoomRepository;

import com.example.quickshare.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
	Room findByNameAndPassword(String name, String password);
    boolean existsByName(String name);
}



