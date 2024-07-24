package com.example.MusicInspector.model;

public enum GeneroMusical {

    ROCK,
    POP,
    HIPHOP,
    RAP,
    ELETRONICA,
    CLASSICA,
    REB,
    REGGAE,
    COUNTRY,
    JAZZ,
    FOLK;

    public String getGeneroDisponiveis() {

        StringBuilder generos = new StringBuilder();
        for (GeneroMusical genero : GeneroMusical.values()) {
            generos.append(genero).append("\n");
        }
        return generos.toString();
    }
}
