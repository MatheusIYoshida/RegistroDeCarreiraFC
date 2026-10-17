package com.example.registrodecarreirafc;

public class Temporada {

    private String nomeTemporada;
    private String clube;
    private int partidas;
    private int gols;
    private int assistencias;
    private String status;
    private boolean tituloConquistado;
    private String posicao;

    public Temporada(String nomeTemporada, String clube, int partidas, int gols,
                     int assistencias, String status, boolean tituloConquistado, String posicao) {
        this.nomeTemporada = nomeTemporada;
        this.clube = clube;
        this.partidas = partidas;
        this.gols = gols;
        this.assistencias = assistencias;
        this.status = status;
        this.tituloConquistado = tituloConquistado;
        this.posicao = posicao;
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

    public String getStatus() {
        return status;
    }

    public boolean isTituloConquistado() {
        return tituloConquistado;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setNomeTemporada(String nomeTemporada) {
        this.nomeTemporada = nomeTemporada;
    }

    public void setClube(String clube) {
        this.clube = clube;
    }

    public void setPartidas(int partidas) {
        this.partidas = partidas;
    }

    public void setGols(int gols) {
        this.gols = gols;
    }

    public void setAssistencias(int assistencias) {
        this.assistencias = assistencias;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTituloConquistado(boolean tituloConquistado) {
        this.tituloConquistado = tituloConquistado;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }
}
