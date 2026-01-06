package com.example.postapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {

    @Insert
    void insert(Post... posts);

    @Update
    void update(Post... posts);

    @Delete
    void delete(Post... posts);

    @Query("SELECT * from post")
    List<Post> getAll();

    @Query("SELECT * FROM post WHERE id = :id LIMIT 1")
    Post getPostById (int id);

    @Query("SELECT * FROM post WHERE userId = :userId")
    List<Post> getPostsByUserId (int userId);

    @Query("SELECT * FROM post WHERE fav = 1")
    List<Post> getFavPosts();
}
