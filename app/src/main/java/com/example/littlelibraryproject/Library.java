package com.example.littlelibraryproject;

public class Library {
    public String libraryName;
    public Double latitude;
    public Double longitude;
    public String genres;
    public String welcomeMessage;
    public String photos;

    public Library() {
    }


    public Library(String libraryName, Double latitude, Double longitude, String genres,
                String welcomeMessage, String photos) {
        this.libraryName = libraryName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.genres = genres;
        this.welcomeMessage = welcomeMessage;
        this.photos = photos;
    }
}
