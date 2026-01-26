package com.example.teste2_juliana_2025116166.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "filmes")
public class Filme {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titulo;
    private String realizador;
    private String tipoFilme;
    private String actorPrincipal;
    private int duracao;
    private int ano;

    public Filme(String titulo, String realizador, String tipoFilme, String actorPrincipal, int duracao, int ano) {
        this.titulo = titulo;
        this.realizador = realizador;
        this.tipoFilme = tipoFilme;
        this.actorPrincipal = actorPrincipal;
        this.duracao = duracao;
        this.ano = ano;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRealizador() {
        return realizador;
    }

    public void setRealizador(String realizador) {
        this.realizador = realizador;
    }

    public String getTipoFilme() {
        return tipoFilme;
    }

    public void setTipoFilme(String tipoFilme) {
        this.tipoFilme = tipoFilme;
    }

    public String getActorPrincipal() {
        return actorPrincipal;
    }

    public void setActorPrincipal(String actorPrincipal) {
        this.actorPrincipal = actorPrincipal;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
