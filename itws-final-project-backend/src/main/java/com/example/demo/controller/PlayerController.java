package com.example.demo.controller;

import com.example.demo.entity.Player;
import com.example.demo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(path = "/api/player")
public class PlayerController {

    private final PlayerService service;

    @GetMapping
    public List<Player> getAll() {
        return service.getPlayers();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Player> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getPlayerById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody Player player) {
        service.createPlayer(player);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Player player) {
        service.updatePlayer(id, player);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deletePlayer(id);
    }
}
