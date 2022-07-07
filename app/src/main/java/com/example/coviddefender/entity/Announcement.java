package com.example.coviddefender.entity;

import com.google.firebase.firestore.PropertyName;

public class Announcement {
    @PropertyName("image")
    String image;
    @PropertyName("description")
    String description;
    @PropertyName("url")
    String url;

    public Announcement() {

    }

    public Announcement(String image, String description, String url) {
        this.description = description;
        this.image = image;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
