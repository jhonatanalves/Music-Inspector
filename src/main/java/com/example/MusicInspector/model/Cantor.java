package com.example.MusicInspector.model;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cantores")
public class Cantor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String nome;
    Integer idade;
    Date dataNascimento;
    String nacionalidade;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "cantor_musica",
            joinColumns = @JoinColumn(name = "cantor_id"),
            inverseJoinColumns = @JoinColumn(name = "musica_id")
    )
    private Set<Musica> musicas;

    @Enumerated(EnumType.STRING)
    TipoCantor tipoCantor;

    public Cantor() {

    }

    public Cantor(String nome, Integer idade, Date dataNascimento, String nacionalidade, TipoCantor tipo) {
        this.nome = nome;
        this.idade = idade;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
        this.tipoCantor = tipo;
        this.musicas = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoCantor getTipoCantor() {
        return tipoCantor;
    }

    public void setTipoCantor(TipoCantor tipoCantor) {
        this.tipoCantor = tipoCantor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Set<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(Set<Musica> musicas) {
        this.musicas = musicas;
    }


    public String dataNascimentoString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(dataNascimento);
    }

    @Override
    public String toString() {
        StringBuilder episodiosString = new StringBuilder();

        String data = dataNascimentoString();

        String resp = """
                nome = %s
                idade = %d
                data de nascimento = %s
                nacionalidade = %s
                tipo = %s
                """;
        return String.format(resp, nome, idade, data, nacionalidade, tipoCantor.toString().toLowerCase());
    }


}
