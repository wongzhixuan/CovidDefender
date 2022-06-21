package com.example.coviddefender.db.hotspot;

public class Hotspot {

    String confirmed_num;
    String recovered_num;
    String death_num;

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
