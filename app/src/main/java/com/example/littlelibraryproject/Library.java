package com.example.littlelibraryproject;

public class Library {
    public String libraryName;
    public String coordinates;
    public String genres;
    public String welcomeMessage;
    public String photos;

    public Library() {
    }


    public Library(String libraryName, String coordinates, String genres,
                String welcomeMessage, String photos) {
        this.libraryName = libraryName;
        this.coordinates = coordinates;
        this.genres = genres;
        this.welcomeMessage = welcomeMessage;
        this.photos = photos;
    }
}
