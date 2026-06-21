package com.example.demo.controller;

import com.example.demo.entity.Tournament;
import com.example.demo.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(path = "/api/tournament")
public class TournamentController {

    private final TournamentService service;

    @GetMapping
    public List<Tournament> getAll() {
        return service.getTournaments();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Tournament> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getTournamentById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody Tournament tournament) {
        service.createTournament(tournament);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Tournament tournament) {
        service.updateTournament(id, tournament);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteTournament(id);
    }
}
