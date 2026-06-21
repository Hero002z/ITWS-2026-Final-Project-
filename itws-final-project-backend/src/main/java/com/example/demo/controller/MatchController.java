package com.example.demo.controller;

import com.example.demo.entity.Match;
import com.example.demo.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(path = "/api/match")
public class MatchController {

    private final MatchService service;

    @GetMapping
    public List<Match> getAll() {
        return service.getMatches();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Match> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getMatchById(id));
    }

    @GetMapping(path = "/tournament/{id}")
    public List<Match> getByTournamentId(@PathVariable Integer id) {
        return service.getMatchesByTournamentId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody Match match) {
        service.createMatch(match);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Match match) {
        service.updateMatch(id, match);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteMatch(id);
    }
}
