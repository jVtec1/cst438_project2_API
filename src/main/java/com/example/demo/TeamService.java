package com.example.demo;

import com.example.demo.entity.Team;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamService {

    private final TeamRepository repo;

    public TeamService(TeamRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Page<Team> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Team get(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Team not found: " + id));
    }

    public Team create(Team team) {
        team.setId(null);
        if (team.getWins() == null) team.setWins(0);
        if (team.getLosses() == null) team.setLosses(0);
        return repo.save(team);
    }

    /** Full or partial update: only non-null fields are applied */
    public Team update(Long id, Team payload) {
        Team t = get(id);

        if (payload.getName() != null) t.setName(payload.getName());
        if (payload.getCity() != null) t.setCity(payload.getCity());
        if (payload.getConference() != null) t.setConference(payload.getConference());
        if (payload.getDivision() != null) t.setDivision(payload.getDivision());
        if (payload.getLogoUrl() != null) t.setLogoUrl(payload.getLogoUrl());
        if (payload.getWins() != null) t.setWins(payload.getWins());
        if (payload.getLosses() != null) t.setLosses(payload.getLosses());

        return repo.save(t);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new EntityNotFoundException("Team not found: " + id);
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Team> search(String conference, String division, String city, String name, Pageable pageable) {
        if (conference != null && !conference.isBlank()) {
            return repo.findByConferenceIgnoreCase(conference, pageable);
        }
        if (division != null && !division.isBlank()) {
            return repo.findByDivisionIgnoreCase(division, pageable);
        }
        if (city != null && !city.isBlank()) {
            return repo.findByCityContainingIgnoreCase(city, pageable);
        }
        if (name != null && !name.isBlank()) {
            return repo.findByNameContainingIgnoreCase(name, pageable);
        }
        return repo.findAll(pageable);
    }

    public Team updateRecord(Long id, Integer wins, Integer losses) {
        Team t = get(id);
        if (wins != null) t.setWins(wins);
        if (losses != null) t.setLosses(losses);
        return repo.save(t);
    }
}
