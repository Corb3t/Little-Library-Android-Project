package com.example.littlelibraryproject;

public class User {
    public String username;
    public String name;
    public String favGenre;
    public String favLibrary;
    public String badge;

    public User() {
    }

    public User(String username, String name, String favGenre, String favLibrary, String badge) {
        this.username = username;
        this.name = name;
        this.favGenre = favGenre;
        this.favLibrary = favLibrary;
        this.badge = badge;
    }
}
