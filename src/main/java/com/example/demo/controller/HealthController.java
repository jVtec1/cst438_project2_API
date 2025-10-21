package com.example.demo.controller;

import com.example.demo.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    private final UserService userService;

    public HealthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/status")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Sports Betting API is running!");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        try {
            // Test database connectivity by counting users
            long userCount = userService.findAll().size();
            response.put("status", "UP");
            response.put("database", "Connected");
            response.put("userCount", userCount);
            response.put("message", "Database connection successful");
        } catch (Exception e) {
            response.put("status", "DOWN");
            response.put("database", "Disconnected");
            response.put("error", e.getMessage());
            response.put("message", "Database connection failed");
        }
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }
}