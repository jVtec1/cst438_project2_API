package com.example.demo;

import com.example.demo.entity.Game;
import com.example.demo.entity.Game.GameStatus;
import com.example.demo.entity.Team;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.sterotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import java.util.Optional;
public interface GameService {
    Page<Game> getAll(Pageable pageable);

    Optional<Game> getByID(Long id);

    Game create(Long homeTeamId, Long awayTeamId, LocalDateTime gameDate, Double pointSpread, Double overUnder);

    Game update(Long id, Long homeTeamId, Long awayTeamId, LocalDateTime gameDate, GameStatus status, Double pointSpread, Double overUnder, Integer getHomeScore, Integer getAwayScore );

    void delete(Long id);

    Page<Game> findByStatus(GameStatus status, Pageable pageable);

    Page<Game> findByTeam(Long teamId, Pageable pageable);

    Page<Game> findByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Game start(Long id);
    Game updateScore(Long id, Integer homeScore, Integer awayScore);
    Game complete(Long id);
    Game cancel(Long id);
}