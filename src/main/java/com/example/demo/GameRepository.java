package com.example.demo;
import com.example.demo.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game,Long>{
    Page<Game> findByStatus(GameStatus status, Pageable pageable);

    Page<Game> findByHomeTeamIdOrAwayTeamId(Long homeTeamId, Long awayTeamId, Pageable, pageable);

    Page<Game> findByGameDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Game> findByHomeTeamIdAndStatus(Long homeTeamId, GameStatus status, Pageable pageable);

    Page<Game> findByAwayTeamIdAndStatus(Long awayTeamId, GameStatus status, Pageable pageable);
}
