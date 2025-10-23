package com.example.demo;

import com.example.demo.entity.Game;
import com.example.demo.entity.Game.GameStatus;
import com.example.demo.entity.Team;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class GameService {

    private final GameRepository games;
    private final TeamRepository teams;

    public GameService(GameRepository games, TeamRepository teams) {
        this.games = games;
        this.teams = teams;
    }


    @Transactional(readOnly = true)
    public Page<Game> list(Pageable pageable) {
        return games.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Game get(Long id) {
        return games.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found: " + id));
    }

    public Game create(Long homeTeamId,
                       Long awayTeamId,
                       LocalDateTime gameDate,
                       Double pointSpread,
                       Double overUnder) {

        Team home = teams.findById(homeTeamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found: " + homeTeamId));
        Team away = teams.findById(awayTeamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found: " + awayTeamId));

        if (home.getId().equals(away.getId())) {
            throw new IllegalArgumentException("Home and away teams must be different.");
        }

        Game g = new Game();
        g.setHomeTeam(home);
        g.setAwayTeam(away);
        g.setGameDate(gameDate);
        g.setPointSpread(pointSpread);
        g.setOverUnder(overUnder);
        g.setStatus(GameStatus.SCHEDULED);
        g.setHomeScore(null);
        g.setAwayScore(null);

        return games.save(g);
    }

    public Game update(Long id,
                       Long homeTeamId,
                       Long awayTeamId,
                       LocalDateTime gameDate,
                       GameStatus status,
                       Double pointSpread,
                       Double overUnder,
                       Integer homeScore,
                       Integer awayScore) {

        Game g = get(id);

        if (homeTeamId != null) {
            Team home = teams.findById(homeTeamId)
                    .orElseThrow(() -> new EntityNotFoundException("Team not found: " + homeTeamId));
            g.setHomeTeam(home);
        }

        if (awayTeamId != null) {
            Team away = teams.findById(awayTeamId)
                    .orElseThrow(() -> new EntityNotFoundException("Team not found: " + awayTeamId));
            g.setAwayTeam(away);
        }

        if (g.getHomeTeam() != null && g.getAwayTeam() != null
                && g.getHomeTeam().getId().equals(g.getAwayTeam().getId())) {
            throw new IllegalArgumentException("Home and away teams must be different.");
        }

        if (gameDate != null) g.setGameDate(gameDate);
        if (pointSpread != null) g.setPointSpread(pointSpread);
        if (overUnder != null) g.setOverUnder(overUnder);

        if (status != null) {
            validateStatusTransition(g.getStatus(), status);
            g.setStatus(status);
        }

        if (homeScore != null || awayScore != null) {
            if (g.getStatus() != GameStatus.IN_PROGRESS && g.getStatus() != GameStatus.COMPLETED) {
                throw new IllegalStateException("Scores can be updated only when IN_PROGRESS or COMPLETED.");
            }
            if (homeScore != null) g.setHomeScore(homeScore);
            if (awayScore != null) g.setAwayScore(awayScore);
        }

        return games.save(g);
    }

    public void delete(Long id) {
        if (!games.existsById(id)) {
            throw new EntityNotFoundException("Game not found: " + id);
        }
        games.deleteById(id);
    }

    // --- Queries ---

    @Transactional(readOnly = true)
    public Page<Game> findByStatus(GameStatus status, Pageable pageable) {
        return games.findByStatus(status, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Game> findByTeam(Long teamId, Pageable pageable) {
        return games.findByHomeTeamIdOrAwayTeamId(teamId, teamId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Game> findByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return games.findByGameDateBetween(start, end, pageable);
    }

    // --- Game lifecycle helpers ---

    public Game start(Long id) {
        Game g = get(id);
        g.setStatus(GameStatus.IN_PROGRESS);
        return games.save(g);
    }

    public Game updateScore(Long id, Integer homeScore, Integer awayScore) {
        Game g = get(id);
        if (g.getStatus() != GameStatus.IN_PROGRESS && g.getStatus() != GameStatus.COMPLETED) {
            throw new IllegalStateException("Scores can be updated only when IN_PROGRESS or COMPLETED.");
        }
        if (homeScore != null) g.setHomeScore(homeScore);
        if (awayScore != null) g.setAwayScore(awayScore);
        return games.save(g);
    }

    public Game complete(Long id) {
        Game g = get(id);
        g.setStatus(GameStatus.COMPLETED);
        return games.save(g);
    }

    public Game cancel(Long id) {
        Game g = get(id);
        g.setStatus(GameStatus.CANCELLED);
        return games.save(g);
    }

    // --- Validation helpers ---
    private void validateStatusTransition(GameStatus current, GameStatus next) {
        if (current == GameStatus.CANCELLED && next != GameStatus.CANCELLED)
            throw new IllegalStateException("Cancelled games cannot change status.");
        if (current == GameStatus.COMPLETED && next != GameStatus.COMPLETED)
            throw new IllegalStateException("Completed game
