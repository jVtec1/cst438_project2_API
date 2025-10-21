package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team {
    
    @Id
    @Column(name = "team_id")
    private Long teamId;
    
    @Column(name = "team_name", nullable = false)
    private String teamName;
    
    @Column(nullable = false)
    private String nickname;
    
    @Column(name = "logo_url")
    private String logoUrl;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String conference; // Eastern or Western
    
    @Column(nullable = false)
    private String division;
    
    private Integer wins;
    
    private Integer losses;


    public Team() {
        this.wins = 0;
        this.losses = 0;
    }

    public Team(Long teamId, String teamName, String nickname, String logoUrl) {
        this();
        this.teamId = teamId;
        this.teamName = teamName;
        this.nickname = nickname;
        this.logoUrl = logoUrl;
    }

    // Getters n Setters
    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }
}
