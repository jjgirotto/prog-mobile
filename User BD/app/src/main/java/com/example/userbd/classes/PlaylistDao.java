package com.example.userbd.classes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlaylistDao {

    @Query("SELECT * FROM playlist")
    List<Playlist> getAll();

    @Query("SELECT * from playlist WHERE id = :id LIMIT 1")
    Playlist findById(int id);
    @Transaction
    @Query("SELECT * FROM User")
    public List<UserWithPlaylists> getUsersWithPlaylists();
    @Transaction
    @Query("SELECT * FROM User where id = :userId LIMIT 1")
    public UserWithPlaylists getUserWithPlaylists(int userId);
    @Insert
    void insert(Playlist... playlists);
    @Update
    void update(Playlist... playlists);
    @Delete
    void delete(Playlist playlist);
}
