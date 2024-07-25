package com.example.MusicInspector.repository;

import com.example.MusicInspector.model.Cantor;
import com.example.MusicInspector.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface CantorRepository  extends JpaRepository<Cantor, Long> {

    @Query("SELECT c from Cantor c WHERE c.nome LIKE :nomeCantor")
    Optional<Cantor> buscarCantor(String nomeCantor);

    @Query("SELECT m from Cantor c JOIN c.musicas m WHERE c.nome LIKE :nomeCantor")
    Set<Musica> buscarMusicasDeUmCantor(String nomeCantor);


    @Query("SELECT m from Cantor c JOIN c.musicas m")
    Set<Musica> buscarMusicasDeTodosCantores();
}
