package com.example.eventapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User... users);
    @Update
    void updateUser(User... users);
    @Delete
    void deleteUser(User... users);
    @Query("SELECT * FROM user")
    List<User> getAll();
    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    User getUserById(int id);
    @Query("SELECT * FROM user WHERE email = :email")
    List<User> findUserByEmail(String email);
    @Query("SELECT * FROM user WHERE name = :name")
    List<User> findUserByName(String name);
}
