package com.example.demo.repository;

import com.example.demo.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    List<Player> findAllByDeletedAtIsNull();

    Optional<Player> findOneByIdAndDeletedAtIsNull(Integer id);

    Boolean existsByFirstNameAndLastNameAndDeletedAtIsNull(String firstName, String lastName);
}
