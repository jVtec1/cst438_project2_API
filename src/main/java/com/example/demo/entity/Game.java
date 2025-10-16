package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "games")
public class Game {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;
    
    @ManyToOne
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;
    
    @Column(name = "game_date", nullable = false)
    private LocalDateTime gameDate;
    
    @Column(name = "home_score")
    private Integer homeScore;
    
    @Column(name = "away_score")
    private Integer awayScore;
    
    @Enumerated(EnumType.STRING)
    private GameStatus status;
    
    @Column(name = "point_spread")
    private Double pointSpread; 
    
    @Column(name = "over_under")
    private Double overUnder; 

    public enum GameStatus {
        SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
    }

    public Game() {
        this.status = GameStatus.SCHEDULED;
    }

    public Game(Team homeTeam, Team awayTeam, LocalDateTime gameDate) {
        this();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameDate = gameDate;
    }

    // Getters n Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDateTime gameDate) {
        this.gameDate = gameDate;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Double getPointSpread() {
        return pointSpread;
    }

    public void setPointSpread(Double pointSpread) {
        this.pointSpread = pointSpread;
    }

    public Double getOverUnder() {
        return overUnder;
    }

    public void setOverUnder(Double overUnder) {
        this.overUnder = overUnder;
    }
}
