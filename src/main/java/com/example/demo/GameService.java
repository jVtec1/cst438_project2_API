package com.example.demo;

import com.example.demo.entity.Game;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameService {
    private final GameRepository repo;
    
    public GameService(GameRepository repo) { this.repo = repo; }
    
    public List<Game> findAll() { return repo.findAll(); }
    public Game findById(Long id) { return repo.findById(id).orElse(null); }
    public Game save(Game game) { return repo.save(game); }
    public void deleteById(Long id) { repo.deleteById(id); }
}