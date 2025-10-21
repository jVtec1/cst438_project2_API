package com.example.demo;

import com.example.demo.entity.Game;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService service;

    public GameController(GameService service) { this.service = service; }

    @GetMapping
    public List<Game> getAllGames() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Game getGame(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return service.save(game);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        service.deleteById(id);
    }
}