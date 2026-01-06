package com.example.postapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private String title;
    private String body;
    private int fav;

    public Post(int id, int userId, String title, String body, int fav) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.fav = fav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }
}
