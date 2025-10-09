package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bets")
public class Bet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BetType betType;
    
    @Column(nullable = false)
    private Double amount;
    
    @Column(nullable = false)
    private Double odds; 
    
    @Column(name = "bet_value")
    private String betValue; 
    
    @Column(name = "placed_at", nullable = false)
    private LocalDateTime placedAt;
    
    @Enumerated(EnumType.STRING)
    private BetStatus status;
    
    @Column(name = "potential_payout")
    private Double potentialPayout;

    public enum BetType {
        MONEYLINE,      
        POINT_SPREAD,   
        OVER_UNDER,     
        PROP_BET        
    }

    public enum BetStatus {
        ACTIVE,        
        WON,           
        LOST,          
        CANCELLED,     
        PUSH           
    }

    public Bet() {
        this.placedAt = LocalDateTime.now();
        this.status = BetStatus.ACTIVE;
    }



    public Bet(User user, Game game, BetType betType, Double amount, Double odds, String betValue) {
        this();
        this.user = user;
        this.game = game;
        this.betType = betType;
        this.amount = amount;
        this.odds = odds;
        this.betValue = betValue;
        this.potentialPayout = calculatePayout(amount, odds);
    }

    // Calculate potential payout based on amount and odds
    private Double calculatePayout(Double amount, Double odds) {
        if (odds > 0) {
            return (odds / 100) * amount + amount;
        } else {

            return (100 / Math.abs(odds)) * amount + amount;
        }
    }

    // Getters n Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
        // Recalculate payout when amount changes
        if (this.odds != null) {
            this.potentialPayout = calculatePayout(amount, this.odds);
        }
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
        // Recalculate payout when odds change
        if (this.amount != null) {
            this.potentialPayout = calculatePayout(this.amount, odds);
        }
    }

    public String getBetValue() {
        return betValue;
    }

    public void setBetValue(String betValue) {
        this.betValue = betValue;
    }

    public LocalDateTime getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(LocalDateTime placedAt) {
        this.placedAt = placedAt;
    }

    public BetStatus getStatus() {
        return status;
    }

    public void setStatus(BetStatus status) {
        this.status = status;
    }

    public Double getPotentialPayout() {
        return potentialPayout;
    }

    public void setPotentialPayout(Double potentialPayout) {
        this.potentialPayout = potentialPayout;
    }
}
