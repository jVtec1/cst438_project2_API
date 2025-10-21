package com.example.demo;

import com.example.demo.entity.Team;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeamService {
    private final TeamRepository repo;
    
    public TeamService(TeamRepository repo) { this.repo = repo; }
    
    public List<Team> findAll() { return repo.findAll(); }
    public Team findById(Long id) { return repo.findById(id).orElse(null); }
    public Team save(Team team) { return repo.save(team); }
    public void deleteById(Long id) { repo.deleteById(id); }
}