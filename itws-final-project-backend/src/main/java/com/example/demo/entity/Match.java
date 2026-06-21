package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "chess_match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chess_match_id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne(optional = false)
    @JoinColumn(name = "white_player_id", nullable = false)
    private Player whitePlayer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "black_player_id", nullable = false)
    private Player blackPlayer;

    @Column(name = "round_number", nullable = false)
    private Integer roundNumber;

    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "played_at", nullable = false)
    private LocalDateTime playedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
