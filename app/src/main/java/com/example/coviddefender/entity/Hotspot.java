package com.example.coviddefender.entity;

import com.google.firebase.firestore.PropertyName;

public class Hotspot {
    @PropertyName("confirmed")
    String confirmed_num;
    @PropertyName("recovered")
    String recovered_num;
    @PropertyName("death")
    String death_num;

    public Hotspot() {
        // empty constructor required
    }

    public Hotspot(String confirmed_num,
                   String recovered_num,
                   String death_num) {
        this.confirmed_num = confirmed_num;
        this.recovered_num = recovered_num;
        this.death_num = death_num;

    }

    public String getConfirmed_num() {
        return confirmed_num;
    }

    public void setConfirmed_num(String confirmed_num) {
        this.confirmed_num = confirmed_num;
    }

    public String getDeath_num() {
        return death_num;
    }

    public void setDeath_num(String death_num) {
        this.death_num = death_num;
    }

    public String getRecovered_num() {
        return recovered_num;
    }

    public void setRecovered_num(String recovered_num) {
        this.recovered_num = recovered_num;
    }
}
