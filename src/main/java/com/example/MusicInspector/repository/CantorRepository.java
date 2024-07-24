package com.example.MusicInspector.repository;

import com.example.MusicInspector.model.Cantor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CantorRepository  extends JpaRepository<Cantor, Long> {

    @Query("SELECT c from Cantor c WHERE c.nome LIKE :nome")
    Optional<Cantor> buscarCantor(String nome);

}
