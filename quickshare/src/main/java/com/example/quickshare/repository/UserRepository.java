package com.example.quickshare.repository;

import com.example.quickshare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsernameAndPassword(String username, String password);
}

