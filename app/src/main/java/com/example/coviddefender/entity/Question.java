package com.example.coviddefender.entity;

import com.google.firebase.firestore.PropertyName;

public class Question {
    @PropertyName("id")
    Integer id;
    @PropertyName("question")
    String question;

    public Question() {
        //Empty constructor required
    }

    public Question(Integer id, String question) {
        this.id = id;
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
