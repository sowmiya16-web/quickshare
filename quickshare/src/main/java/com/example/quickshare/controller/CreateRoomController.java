package com.example.quickshare.controller;

import com.example.quickshare.model.FileEntity;
import com.example.quickshare.model.Room;
import com.example.quickshare.repository.FileRepository;
import com.example.quickshare.repository.RoomRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class CreateRoomController {

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private FileRepository fileRepo;

    @GetMapping("/createRoom")
    public String showCreateRoom(HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        return "createRoom";
    }

    @PostMapping("/createRoom")
    public String createRoom(@RequestParam String roomName,
                             @RequestParam String roomPassword,
                             HttpSession session,
                             Model model) {
        if (roomRepo.existsByName(roomName)) {
            model.addAttribute("msg", "Room already exists");
            return "createRoom";
        }
        Room room = new Room(null, roomName, roomPassword);
        roomRepo.save(room);
        session.setAttribute("createdRoom", roomName);
        return "redirect:/uploadFile";
    }

    @GetMapping("/uploadFile")
    public String uploadPage(HttpSession session) {
        if (session.getAttribute("createdRoom") == null) return "redirect:/dashboard";
        return "uploadFile";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             HttpSession session, Model model) throws IOException {
        String roomName = (String) session.getAttribute("createdRoom");
        if (roomName == null || file.isEmpty()) {
            model.addAttribute("msg", "Room or file missing!");
            return "uploadFile";
        }
        String uploadDir = "uploads/";
        new File(uploadDir).mkdirs();
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + fileName);
        file.transferTo(dest);
        model.addAttribute("msg", "File uploaded successfully!");
        return "uploadFile";
    }
}
