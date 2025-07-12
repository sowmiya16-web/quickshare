package com.example.quickshare;

import com.example.quickshare.model.User;
import com.example.quickshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuickshareApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(QuickshareApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Insert dummy user only if not already present
        if (!userRepository.existsById("admin")) {
            userRepository.save(new User("admin", "admin123"));
        }
    }
}

