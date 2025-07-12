package com.example.quickshare.controller;


import com.example.quickshare.model.FileEntity;
import com.example.quickshare.model.Room;
import com.example.quickshare.repository.FileRepository;
import com.example.quickshare.repository.RoomRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class JoinRoomController {

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private FileRepository fileRepo;

    @GetMapping("/joinRoom")
    public String joinRoomPage(HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        return "joinRoom";
    }

    @PostMapping("/joinRoom")
    public String joinRoom(@RequestParam String roomName,
                           @RequestParam String roomPassword,
                           HttpSession session, Model model) {

        Room room = roomRepo.findByNameAndPassword(roomName, roomPassword);
        if (room == null) {
            model.addAttribute("msg", "Room not found or wrong password");
            return "joinRoom";
        }

        List<FileEntity> files = fileRepo.findByRoomName(roomName);

        model.addAttribute("roomName", roomName);   // ✅ Needed to generate file URLs
        model.addAttribute("files", files);         // List of uploaded files

        return "viewFiles"; // ✅ Create this HTML file to show the files
    }
}

