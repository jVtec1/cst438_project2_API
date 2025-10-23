package com.example.demo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    // ---- Request models (kept inside this file to avoid extra classes) ----
    public static class CreateTeamRequest {
        @NotBlank public String name;
        @NotBlank public String city;
        @NotBlank public String conference;
        @NotBlank public String division;
        public String logoUrl;
        @PositiveOrZero public Integer wins;
        @PositiveOrZero public Integer losses;
    }

    public static class UpdateTeamRequest {
        public String name;
        public String city;
        public String conference;
        public String division;
        public String logoUrl;
        @PositiveOrZero public Integer wins;
        @PositiveOrZero public Integer losses;
    }

    // ---- Endpoints ----

    // List with pagination
    @GetMapping
    public Page<Team> list(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(pageable);
    }

    // Get one
    @GetMapping("/{id}")
    public Team get(@PathVariable Long id) {
        return service.get(id);
    }

    // Create (JSON)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Team create(@Valid @RequestBody CreateTeamRequest req) {
        Team t = new Team();
        t.setName(req.name);
        t.setCity(req.city);
        t.setConference(req.conference);
        t.setDivision(req.division);
        t.setLogoUrl(req.logoUrl);
        t.setWins(req.wins);
        t.setLosses(req.losses);
        return service.create(t);
    }

    // Update (JSON) — partial allowed; null fields are ignored
    @PutMapping("/{id}")
    public Team update(@PathVariable Long id, @Valid @RequestBody UpdateTeamRequest req) {
        Team patch = new Team();
        patch.setName(req.name);
        patch.setCity(req.city);
        patch.setConference(req.conference);
        patch.setDivision(req.division);
        patch.setLogoUrl(req.logoUrl);
        patch.setWins(req.wins);
        patch.setLosses(req.losses);
        return service.update(id, patch);
    }

    // Update record only (wins/losses) via JSON
    public static class RecordRequest {
        @PositiveOrZero public Integer wins;
        @PositiveOrZero public Integer losses;
    }

    @PatchMapping("/{id}/record")
    public Team updateRecord(@PathVariable Long id, @RequestBody RecordRequest req) {
        return service.updateRecord(id, req.wins, req.losses);
    }

    // Search: keep filters in query params
    @GetMapping("/search")
    public Page<Team> search(@RequestParam(required = false) String conference,
                             @RequestParam(required = false) String division,
                             @RequestParam(required = false) String city,
                             @RequestParam(required = false) String name,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "20") int size) {
        return service.search(conference, division, city, name, PageRequest.of(page, size));
    }

    // Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // ---- Lightweight error mapping (kept here to avoid another file) ----
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, String> handleNotFound(EntityNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleBadRequest(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }
}
