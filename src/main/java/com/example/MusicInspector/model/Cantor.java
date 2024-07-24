package com.example.MusicInspector.model;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cantores")
public class Cantor {

    //https://www.baeldung.com/hibernate-many-to-many
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String nome;
    Integer idade;
    Date dataNascimento;
    String nacionalidade;

    //O atributo mappedBy na anotação @OneToMany em JPA deve especificar o nome do atributo na classe alvo (no lado "muitos" da relação)
    //que mantém a referência para a classe proprietária (lado "um" da relação).
    @ManyToMany(mappedBy = "cantores", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    List<Musica> musicas;

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

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
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
