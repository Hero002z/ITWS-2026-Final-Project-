package com.example.demo.service;

import com.example.demo.entity.Match;
import com.example.demo.entity.Player;
import com.example.demo.entity.Tournament;
import com.example.demo.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository repository;

    public List<Match> getMatches() {
        return repository.findAllByDeletedAtIsNull();
    }

    public List<Match> getMatchesByTournamentId(Integer id) {
        return repository.findAllByTournamentIdAndDeletedAtIsNull(id);
    }

    public Optional<Match> getMatchById(Integer id) {
        return repository.findOneByIdAndDeletedAtIsNull(id);
    }

    public void createMatch(Match match) {
        checkMatchConsistency(match);

        Match newMatch = new Match();

        Tournament tournament = new Tournament();
        tournament.setId(match.getTournament().getId());

        Player whitePlayer = new Player();
        whitePlayer.setId(match.getWhitePlayer().getId());

        Player blackPlayer = new Player();
        blackPlayer.setId(match.getBlackPlayer().getId());

        newMatch.setTournament(tournament);
        newMatch.setWhitePlayer(whitePlayer);
        newMatch.setBlackPlayer(blackPlayer);
        newMatch.setRoundNumber(match.getRoundNumber());
        newMatch.setResult(match.getResult());
        newMatch.setPlayedAt(match.getPlayedAt());
        newMatch.setCreatedAt(LocalDateTime.now());

        repository.save(newMatch);
    }

    public void updateMatch(Integer id, Match match) {
        checkMatchConsistency(match);

        Match existing = repository.findOneByIdAndDeletedAtIsNull(id)
                .orElseThrow();

        Tournament tournament = new Tournament();
        tournament.setId(match.getTournament().getId());

        Player whitePlayer = new Player();
        whitePlayer.setId(match.getWhitePlayer().getId());

        Player blackPlayer = new Player();
        blackPlayer.setId(match.getBlackPlayer().getId());

        existing.setTournament(tournament);
        existing.setWhitePlayer(whitePlayer);
        existing.setBlackPlayer(blackPlayer);
        existing.setRoundNumber(match.getRoundNumber());
        existing.setResult(match.getResult());
        existing.setPlayedAt(match.getPlayedAt());
        existing.setUpdatedAt(LocalDateTime.now());

        repository.save(existing);
    }

    public void deleteMatch(Integer id) {
        Match existing = repository.findOneByIdAndDeletedAtIsNull(id)
                .orElseThrow();

        existing.setDeletedAt(LocalDateTime.now());

        repository.save(existing);
    }

    private void checkMatchConsistency(Match match) {

        if (match.getTournament() == null || match.getTournament().getId() == null) {
            throw new RuntimeException("MATCH_TOURNAMENT_REQUIRED");
        }

        if (match.getWhitePlayer() == null || match.getWhitePlayer().getId() == null) {
            throw new RuntimeException("MATCH_WHITE_PLAYER_REQUIRED");
        }

        if (match.getBlackPlayer() == null || match.getBlackPlayer().getId() == null) {
            throw new RuntimeException("MATCH_BLACK_PLAYER_REQUIRED");
        }

        if (match.getWhitePlayer().getId()
                .equals(match.getBlackPlayer().getId())) {

            throw new RuntimeException("MATCH_PLAYERS_MUST_BE_DIFFERENT");
        }

        if (match.getRoundNumber() == null || match.getRoundNumber() <= 0) {
            throw new RuntimeException("MATCH_ROUND_NUMBER_INVALID");
        }

        if (match.getResult() == null || match.getResult().isBlank()) {
            throw new RuntimeException("MATCH_RESULT_REQUIRED");
        }

        if (match.getPlayedAt() == null) {
            throw new RuntimeException("MATCH_PLAYED_AT_REQUIRED");
        }
    }
}
