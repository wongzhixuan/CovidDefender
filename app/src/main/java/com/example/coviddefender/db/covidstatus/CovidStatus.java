package com.example.coviddefender.db.covidstatus;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

// POJO class to represent the schema
@Entity(tableName = "covid_status_table")
public class CovidStatus {
    @PrimaryKey (autoGenerate = true)
    public int id;
    private String update_time;
    private String covid_status;
    private String location_risk;
    private String dependent_risk;

    // Constructor
    public CovidStatus(String update_time, String covid_status, String location_risk,String dependent_risk)
    {
        this.update_time = update_time;
        this.covid_status = covid_status;
        this.location_risk = location_risk;
        this.dependent_risk = dependent_risk;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getCovid_status() {
        return covid_status;
    }

    public String getDependent_risk() {
        return dependent_risk;
    }

    public String getLocation_risk() {
        return location_risk;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setCovid_status(String covid_status) {
        this.covid_status = covid_status;
    }

    public void setDependent_risk(String dependent_risk) {
        this.dependent_risk = dependent_risk;
    }

    public void setLocation_risk(String location_risk) {
        this.location_risk = location_risk;
    }
}
