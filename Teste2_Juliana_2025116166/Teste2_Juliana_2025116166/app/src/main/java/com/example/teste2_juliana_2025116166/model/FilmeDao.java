package com.example.teste2_juliana_2025116166.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FilmeDao {

    @Insert
    void insert(Filme... filmes);

    @Query("SELECT * FROM filmes")
    List<Filme> getAll();

    @Query("SELECT * FROM filmes WHERE id = :id")
    Filme getById(int id);


}
