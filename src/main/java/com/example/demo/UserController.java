// src/main/java/com/example/demo/UserController.java
package com.example.demo;

import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) { this.service = service; }

    @GetMapping
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.save(user);
    }

    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = request.get("username");
            String password = request.get("password");
            String email = request.get("email");
            
            if (service.isUsernameAvailable(username)) {
                User user = new User(username, email);
                user.setPassword(password);
                service.save(user);
                response.put("success", true);
                response.put("message", "User registered successfully");
            } else {
                response.put("success", false);
                response.put("message", "Username already exists");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = request.get("username");
            String password = request.get("password");
            
            if (service.verifyUserLogin(username, password)) {
                User user = service.findByUsername(username);
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("user", user);
            } else {
                response.put("success", false);
                response.put("message", "Invalid username or password");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Login failed: " + e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteById(id);
    }
}
