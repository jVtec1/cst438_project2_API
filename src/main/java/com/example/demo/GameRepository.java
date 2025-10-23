package com.example.demo;

import com.example.demo.entity.Game;
import com.example.demo.entity.Game.GameStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface GameRepository extends JpaRepository<Game,Long>{
    Page<Game> findByStatus(GameStatus status, Pageable pageable);

    Page<Game> findByHomeTeamIdOrAwayTeamId(Long homeTeamId, Long awayTeamId, Pageable pageable);

    Page<Game> findByGameDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

}
