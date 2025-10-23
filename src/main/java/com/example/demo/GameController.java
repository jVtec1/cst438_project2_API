package com.example.demo;

import com.example.demo.game.entity.Game;
import com.example.demo.game.entity.Game.GameStatus;
import com.example.demo.game.service.GameService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    // ====== JSON request models (kept INSIDE this file to avoid new classes) ======
    public static class CreateGameRequest {
        @NotNull private Long homeTeamId;
        @NotNull private Long awayTeamId;
        @NotNull private LocalDateTime gameDate;
        private Double pointSpread;
        private Double overUnder;

        public Long getHomeTeamId() { return homeTeamId; }
        public void setHomeTeamId(Long homeTeamId) { this.homeTeamId = homeTeamId; }
        public Long getAwayTeamId() { return awayTeamId; }
        public void setAwayTeamId(Long awayTeamId) { this.awayTeamId = awayTeamId; }
        public LocalDateTime getGameDate() { return gameDate; }
        public void setGameDate(LocalDateTime gameDate) { this.gameDate = gameDate; }
        public Double getPointSpread() { return pointSpread; }
        public void setPointSpread(Double pointSpread) { this.pointSpread = pointSpread; }
        public Double getOverUnder() { return overUnder; }
        public void setOverUnder(Double overUnder) { this.overUnder = overUnder; }
    }

    public static class UpdateGameRequest {
        private Long homeTeamId;
        private Long awayTeamId;
        private LocalDateTime gameDate;
        private GameStatus status;
        private Double pointSpread;
        private Double overUnder;
        @PositiveOrZero private Integer homeScore;
        @PositiveOrZero private Integer awayScore;

        public Long getHomeTeamId() { return homeTeamId; }
        public void setHomeTeamId(Long homeTeamId) { this.homeTeamId = homeTeamId; }
        public Long getAwayTeamId() { return awayTeamId; }
        public void setAwayTeamId(Long awayTeamId) { this.awayTeamId = awayTeamId; }
        public LocalDateTime getGameDate() { return gameDate; }
        public void setGameDate(LocalDateTime gameDate) { this.gameDate = gameDate; }
        public GameStatus getStatus() { return status; }
        public void setStatus(GameStatus status) { this.status = status; }
        public Double getPointSpread() { return pointSpread; }
        public void setPointSpread(Double pointSpread) { this.pointSpread = pointSpread; }
        public Double getOverUnder() { return overUnder; }
        public void setOverUnder(Double overUnder) { this.overUnder = overUnder; }
        public Integer getHomeScore() { return homeScore; }
        public void setHomeScore(Integer homeScore) { this.homeScore = homeScore; }
        public Integer getAwayScore() { return awayScore; }
        public void setAwayScore(Integer awayScore) { this.awayScore = awayScore; }
    }

    // ====== Endpoints ======

    // List (pagination params stay in the URL — this is normal)
    @GetMapping
    public Page<Game> list(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    public Game get(@PathVariable Long id) {
        return service.get(id);
    }

    // CREATE: JSON body
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game create(@Valid @RequestBody CreateGameRequest req) {
        return service.create(
                req.getHomeTeamId(),
                req.getAwayTeamId(),
                req.getGameDate(),
                req.getPointSpread(),
                req.getOverUnder()
        );
    }

    // UPDATE: JSON body (full or partial—any nulls are ignored)
    @PutMapping("/{id}")
    public Game update(@PathVariable Long id, @Valid @RequestBody UpdateGameRequest req) {
        return service.update(
                id,
                req.getHomeTeamId(),
                req.getAwayTeamId(),
                req.getGameDate(),
                req.getStatus(),
                req.getPointSpread(),
                req.getOverUnder(),
                req.getHomeScore(),
                req.getAwayScore()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // Filters (queries belong in the URL)
    @GetMapping("/search/status")
    public Page<Game> byStatus(@RequestParam GameStatus status,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size) {
        return service.findByStatus(status, PageRequest.of(page, size));
    }

    @GetMapping("/search/team/{teamId}")
    public Page<Game> byTeam(@PathVariable Long teamId,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "20") int size) {
        return service.findByTeam(teamId, PageRequest.of(page, size));
    }

    @GetMapping("/search/range")
    public Page<Game> byRange(@RequestParam LocalDateTime start,
                              @RequestParam LocalDateTime end,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size) {
        return service.findByDateRange(start, end, PageRequest.of(page, size));
    }

    // Lightweight error mapping (kept here so you don’t need a new file)
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalStateException.class)
    public Map<String, String> handleConflict(IllegalStateException ex) {
        return Map.of("error", ex.getMessage());
    }
}
