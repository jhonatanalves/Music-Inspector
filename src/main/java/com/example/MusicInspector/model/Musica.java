package com.example.MusicInspector.model;

import jakarta.persistence.*;

import java.util.List;
import java.time.Duration;

@Entity
@Table(name = "musicas")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String nome;
    String letra;
    Duration duracao;
    GeneroMusical genero;

    @ManyToMany(mappedBy = "musicas", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    List<Cantor> cantores;

    public Musica() {

    }

    public Musica(String nome, String letra, Duration duracao, GeneroMusical genero) {
        this.nome = nome;
        this.letra = letra;
        this.duracao = duracao;
        this.genero = genero;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public GeneroMusical getGenero() {
        return genero;
    }

    public void setGenero(GeneroMusical genero) {
        this.genero = genero;
    }

    public List<Cantor> getCantores() {
        return cantores;
    }

    public void setCantores(List<Cantor> cantores) {
        this.cantores = cantores;
    }

    @Override
    public String toString() {
        StringBuilder musicaString = new StringBuilder();

        String resp = """
                nome = %s
                gênero = %s
                duração = %02d:%02d
                letra = %s
                """;
        return String.format(resp, nome, genero.toString().toLowerCase(), duracao.toMinutes(), duracao.toSecondsPart(),letra);
    }
}
