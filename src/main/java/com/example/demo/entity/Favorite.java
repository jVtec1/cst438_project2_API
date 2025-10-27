package com.example.demo.entity;package com.example.demo.entity;



import jakarta.persistence.*;import jakarta.persistence.*;



@Entity@Entity

@Table(name = "favorites")@Table(name = "favorites")

public class Favorite {public class Favorite {

        

    @Id    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;    private Long id;

        

    @ManyToOne    @ManyToOne

    @JoinColumn(name = "user_id", nullable = false)    @JoinColumn(name = "user_id", nullable = false)

    private User user;    private User user;

        

    @ManyToOne    @ManyToOne

    @JoinColumn(name = "team_id", nullable = false)    @JoinColumn(name = "team_id", nullable = false)

    private Team team;    private Team team;



    public Favorite() {}    public Favorite() {}



    public Favorite(User user, Team team) {    public Favorite(User user, Team team) {

        this.user = user;        this.user = user;

        this.team = team;        this.team = team;

    }    }



    // Getters and Setters    // Getters and Setters

    public Long getId() {    public Long getId() {

        return id;        return id;

    }    }



    public void setId(Long id) {    public void setId(Long id) {

        this.id = id;        this.id = id;

    }    }



    public User getUser() {    public User getUser() {

        return user;        return user;

    }    }



    public void setUser(User user) {    public void setUser(User user) {

        this.user = user;        this.user = user;

    }    }



    public Team getTeam() {    public Team getTeam() {

        return team;        return team;

    }    }



    public void setTeam(Team team) {    public void setTeam(Team team) {

        this.team = team;        this.team = team;

    }    }

}}