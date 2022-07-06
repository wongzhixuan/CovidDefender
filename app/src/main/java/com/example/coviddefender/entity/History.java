package com.example.coviddefender.entity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;

import java.util.Date;

public class History {
    @PropertyName("location")
    public String location;
    @PropertyName("time")
    public Timestamp time;
    @PropertyName("IsCheckOut")
    public Boolean IsCheckOut;

    public History(){
        // empty constructor required
    }

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
