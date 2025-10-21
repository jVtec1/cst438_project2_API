// src/main/java/com/example/demo/UserService.java
package com.example.demo;

import com.example.demo.entity.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }

    public List<User> findAll() { return repo.findAll(); }
    public User findById(Long id) { return repo.findById(id).orElse(null); }
    public User save(User user) { return repo.save(user); }
    public void deleteById(Long id) { repo.deleteById(id); }
    
    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
    
    public boolean isUsernameAvailable(String username) {
        return repo.findByUsername(username) == null;
    }
    
    public boolean verifyUserLogin(String username, String password) {
        User user = repo.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
