package com.example.littlelibraryproject;

public class User {
    public String username;
    public String favGenre;
    public String favLibrary;
    public String badge;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String favGenre, String favLibrary, String badge) {
        this.username = username;
        this.favGenre = favGenre;
        this.favLibrary = favLibrary;
        this.badge = badge;
    }



}
