package com.example.coviddefender.entity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;

public class CovidStatus {
    @PropertyName("update_time")
    public Timestamp update_time;
    @PropertyName("covid_status")
    private String covid_status;
    @PropertyName("location_risk")
    private String location_risk;
    @PropertyName("dependent_risk")
    private String dependent_risk;

    public CovidStatus() {
        // empty constructor required
    }

    // Constructor
    public CovidStatus(String covid_status, String dependent_risk, String location_risk, Timestamp update_time) {
        this.update_time = update_time;
        this.covid_status = covid_status;
        this.location_risk = location_risk;
        this.dependent_risk = dependent_risk;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public String getCovid_status() {
        return covid_status;
    }

    public void setCovid_status(String covid_status) {
        this.covid_status = covid_status;
    }

    public String getDependent_risk() {
        return dependent_risk;
    }

    public void setDependent_risk(String dependent_risk) {
        this.dependent_risk = dependent_risk;
    }

    public String getLocation_risk() {
        return location_risk;
    }

    public void setLocation_risk(String location_risk) {
        this.location_risk = location_risk;
    }
}
