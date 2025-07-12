package com.example.quickshare.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.example.quickshare.model.Room;

import com.example.quickshare.model.User;
import com.example.quickshare.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        User user = userRepo.findByUsernameAndPassword(username, password);
        if (user != null) {
            session.setAttribute("user", username);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("msg", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}