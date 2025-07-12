package com.example.quickshare.repository;

import com.example.quickshare.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByRoomName(String roomName);
}


