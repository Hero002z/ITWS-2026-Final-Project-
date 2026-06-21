package com.example.demo.repository;

import com.example.demo.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    List<Match> findAllByDeletedAtIsNull();

    Optional<Match> findOneByIdAndDeletedAtIsNull(Integer id);

    List<Match> findAllByTournamentIdAndDeletedAtIsNull(Integer id);
}
