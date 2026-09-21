package com.example.registrodecarreirafc;

public class Temporada {

    private final String nomeTemporada;
    private final String clube;
    private final int partidas;
    private final int gols;
    private final int assistencias;

    public Temporada(String nomeTemporada, String clube, int partidas, int gols, int assistencias) {
        this.nomeTemporada = nomeTemporada;
        this.clube = clube;
        this.partidas = partidas;
        this.gols = gols;
        this.assistencias = assistencias;
    }

    public String getNomeTemporada() {
        return nomeTemporada;
    }

    public String getClube() {
        return clube;
    }

    public int getPartidas() {
        return partidas;
    }

    public int getGols() {
        return gols;
    }

    public int getAssistencias() {
        return assistencias;
    }
}
