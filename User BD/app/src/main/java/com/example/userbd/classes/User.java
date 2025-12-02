package com.example.userbd.classes;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_name")
    private String userName;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "password")
    private String password;
    public User(String userName, String password, String email) {
        super();
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @NonNull
    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + ", email=" + email + "]";
    }

}
