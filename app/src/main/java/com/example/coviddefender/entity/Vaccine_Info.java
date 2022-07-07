package com.example.coviddefender.entity;

import com.google.firebase.firestore.PropertyName;

public class Vaccine_Info {
    @PropertyName("image")
    String image;
    @PropertyName("description")
    String description;
    @PropertyName("url")
    String url;

    public Vaccine_Info() {
    }

    public Vaccine_Info(String image, String description, String url) {
        this.description = description;
        this.url = url;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }
}
