package com.example.quickshare.controller;

import com.example.quickshare.model.FileEntity;
import com.example.quickshare.repository.FileRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;

@Controller
public class uploadFileController {

    @Autowired
    private FileRepository fileRepo;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("roomName") String roomName,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession session) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/" + roomName + "/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            File dest = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(dest);

            // Save file info to database
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFilename(file.getOriginalFilename());
            fileEntity.setRoomName(roomName);
            fileRepo.save(fileEntity);  // 🔥 important

            redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "File upload failed: " + e.getMessage());
        }

        return "redirect:/dashboard";
    }
}
