package org.example.project.repository;

import jakarta.validation.constraints.NotNull;
import org.example.project.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(nativeQuery = true, value = """
            SELECT c FROM Client c
            LEFT JOIN FETCH c.emails
            LEFT JOIN FETCH c.phones p
            WHERE c.id = ?1""")
    Optional<Client> findById(Long id);

    @Query(nativeQuery = true, value = """
        SELECT c FROM Client c
        LEFT JOIN FETCH c.emails e
        LEFT JOIN FETCH c.phones p""")
    List<Client> findAll();

}
