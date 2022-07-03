package com.example.coviddefender.entity;

public class History {
    public String location;
    public String time;
    public String IsCheckOut;

    public History(String IsCheckOut, String location, String time){
        this.IsCheckOut = IsCheckOut;
        this.location = location;
        this.time = time;
    }

    public String getCheckOut() {
        return IsCheckOut;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }

    public void setCheckOut(String checkOut) {
        IsCheckOut = checkOut;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
