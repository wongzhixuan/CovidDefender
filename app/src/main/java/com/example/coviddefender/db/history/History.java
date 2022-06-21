package com.example.coviddefender.db.history;

public class History {
    public String location;
    public String time;
    public Boolean IsCheckOut;

    public History(Boolean IsCheckOut, String location, String time){
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

    public String getTime() {
        return time;
    }

    public void setCheckOut(Boolean checkOut) {
        IsCheckOut = checkOut;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
