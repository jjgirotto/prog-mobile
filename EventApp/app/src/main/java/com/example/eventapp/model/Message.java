package com.example.eventapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private int id;
    private String username;
    private String text;
    private final LocalDateTime date;
    public String getFormattedDate() { return formatter.format(date); }

    public Message(int id, String username, String text, LocalDateTime date) {
        this.id = id;
        this.username = username;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
