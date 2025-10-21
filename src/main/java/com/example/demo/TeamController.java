package com.example.demo;

import com.example.demo.entity.Team;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService service;

    public TeamController(TeamService service) { this.service = service; }

    @GetMapping
    public List<Team> getAllTeams() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Team getTeam(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return service.save(team);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id) {
        service.deleteById(id);
    }
}