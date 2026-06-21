package com.example.demo.service;

import com.example.demo.entity.Player;
import com.example.demo.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository repository;

    public List<Player> getPlayers() {
        return repository.findAllByDeletedAtIsNull();
    }

    public Optional<Player> getPlayerById(Integer id) {
        return repository.findOneByIdAndDeletedAtIsNull(id);
    }

    public void createPlayer(Player player) {
        checkPlayerConsistency(player);

        if (repository.existsByFirstNameAndLastNameAndDeletedAtIsNull(
                player.getFirstName(),
                player.getLastName()
        )) {
            throw new RuntimeException("PLAYER_ALREADY_EXISTS");
        }

        Player newPlayer = new Player();
        newPlayer.setFirstName(player.getFirstName());
        newPlayer.setLastName(player.getLastName());
        newPlayer.setCountry(player.getCountry());
        newPlayer.setRating(player.getRating());
        newPlayer.setTitle(player.getTitle());
        newPlayer.setCreatedAt(LocalDateTime.now());

        repository.save(newPlayer);
    }

    public void updatePlayer(Integer id, Player player) {
        checkPlayerConsistency(player);

        Player existing = repository.findOneByIdAndDeletedAtIsNull(id)
                .orElseThrow();

        existing.setFirstName(player.getFirstName());
        existing.setLastName(player.getLastName());
        existing.setCountry(player.getCountry());
        existing.setRating(player.getRating());
        existing.setTitle(player.getTitle());
        existing.setUpdatedAt(LocalDateTime.now());

        repository.save(existing);
    }

    public void deletePlayer(Integer id) {
        Player existing = repository.findOneByIdAndDeletedAtIsNull(id)
                .orElseThrow();

        existing.setDeletedAt(LocalDateTime.now());

        repository.save(existing);
    }

    private void checkPlayerConsistency(Player player) {

        if (player.getFirstName() == null || player.getFirstName().isBlank()) {
            throw new RuntimeException("PLAYER_FIRST_NAME_CANT_BE_EMPTY");
        }

        if (player.getLastName() == null || player.getLastName().isBlank()) {
            throw new RuntimeException("PLAYER_LAST_NAME_CANT_BE_EMPTY");
        }

        if (player.getCountry() == null || player.getCountry().isBlank()) {
            throw new RuntimeException("PLAYER_COUNTRY_CANT_BE_EMPTY");
        }

        if (player.getRating() == null || player.getRating() <= 0) {
            throw new RuntimeException("PLAYER_RATING_INVALID");
        }

        if (player.getTitle() == null || player.getTitle().isBlank()) {
            throw new RuntimeException("PLAYER_TITLE_CANT_BE_EMPTY");
        }
    }
}
