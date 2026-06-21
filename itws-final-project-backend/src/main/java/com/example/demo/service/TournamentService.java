package com.example.demo.service;

import com.example.demo.entity.Tournament;
import com.example.demo.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository repository;

    public List<Tournament> getTournaments() {
        return repository.findAllByDeletedAtIsNull();
    }

    public Optional<Tournament> getTournamentById(Integer id) {
        return repository.findOneByIdAndDeletedAtIsNull(id);
    }

    public void createTournament(Tournament tournament) {
        checkTournamentConsistency(tournament);

        if (repository.existsByNameAndDeletedAtIsNull(tournament.getName())) {
            throw new RuntimeException("TOURNAMENT_ALREADY_EXISTS");
        }

        Tournament newTournament = new Tournament();
        newTournament.setName(tournament.getName());
        newTournament.setLocation(tournament.getLocation());
        newTournament.setStartDate(tournament.getStartDate());
        newTournament.setEndDate(tournament.getEndDate());
        newTournament.setTimeControl(tournament.getTimeControl());
        newTournament.setCreatedAt(LocalDateTime.now());

        repository.save(newTournament);
    }

    public void updateTournament(Integer id, Tournament tournament) {
        checkTournamentConsistency(tournament);

        Tournament existing = repository.findOneByIdAndDeletedAtIsNull(id)
                .orElseThrow();

        existing.setName(tournament.getName());
        existing.setLocation(tournament.getLocation());
        existing.setStartDate(tournament.getStartDate());
        existing.setEndDate(tournament.getEndDate());
        existing.setTimeControl(tournament.getTimeControl());
        existing.setUpdatedAt(LocalDateTime.now());

        repository.save(existing);
    }

    public void deleteTournament(Integer id) {
        Tournament existing = repository.findOneByIdAndDeletedAtIsNull(id)
                .orElseThrow();

        existing.setDeletedAt(LocalDateTime.now());

        repository.save(existing);
    }

    private void checkTournamentConsistency(Tournament tournament) {

        if (tournament.getName() == null || tournament.getName().isBlank()) {
            throw new RuntimeException("TOURNAMENT_NAME_CANT_BE_EMPTY");
        }

        if (tournament.getLocation() == null || tournament.getLocation().isBlank()) {
            throw new RuntimeException("TOURNAMENT_LOCATION_CANT_BE_EMPTY");
        }

        if (tournament.getStartDate() == null) {
            throw new RuntimeException("TOURNAMENT_START_DATE_REQUIRED");
        }

        if (tournament.getEndDate() == null) {
            throw new RuntimeException("TOURNAMENT_END_DATE_REQUIRED");
        }

        if (tournament.getEndDate().isBefore(tournament.getStartDate())) {
            throw new RuntimeException("TOURNAMENT_END_DATE_INVALID");
        }

        if (tournament.getTimeControl() == null || tournament.getTimeControl().isBlank()) {
            throw new RuntimeException("TOURNAMENT_TIME_CONTROL_REQUIRED");
        }
    }
}
