package com.example.coviddefender.entity;

import com.google.firebase.Timestamp;

public class History {

    public String location;
    public Timestamp time;
    public Boolean IsCheckOut;

    public History(Boolean IsCheckOut, String location, Timestamp time){
        this.IsCheckOut = IsCheckOut;
        this.location = location;
        this.time = time;
    }

    public Boolean getCheckOut() {
        return IsCheckOut;
    }

    public String getLocation() {
        return location;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setCheckOut(Boolean checkOut) {
        IsCheckOut = checkOut;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
