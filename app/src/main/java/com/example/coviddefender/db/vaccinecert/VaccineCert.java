package com.example.coviddefender.db.vaccinecert;

public class VaccineCert {
    String username;
    String userIC;
    String dose1_date;
    String dose1_manufacturer;
    String dose1_facility;
    String dose1_batch;
    String dose2_date;
    String dose2_manufacturer;
    String dose2_facility;
    String dose2_batch;

    public VaccineCert(String username,
                       String userIC,
                       String dose1_date,
                       String dose1_manufacturer,
                       String dose1_facility,
                       String dose1_batch,
                       String dose2_date,
                       String dose2_manufacturer,
                       String dose2_facility,
                       String dose2_batch) {
        this.username = username;
        this.userIC = userIC;
        this.dose1_date = dose1_date;
        this.dose1_manufacturer = dose1_manufacturer;
        this.dose1_facility = dose1_facility;
        this.dose1_batch = dose1_batch;
        this.dose2_date = dose2_date;
        this.dose2_manufacturer = dose2_manufacturer;
        this.dose2_facility = dose2_facility;
        this.dose2_batch = dose2_batch;
    }

    public String getDose1_batch() {
        return dose1_batch;
    }

    public void setDose1_batch(String dose1_batch) {
        this.dose1_batch = dose1_batch;
    }

    public String getDose1_date() {
        return dose1_date;
    }

    public void setDose1_date(String dose1_date) {
        this.dose1_date = dose1_date;
    }

    public String getDose1_facility() {
        return dose1_facility;
    }

    public void setDose1_facility(String dose1_facility) {
        this.dose1_facility = dose1_facility;
    }

    public String getDose1_manufacturer() {
        return dose1_manufacturer;
    }

    public void setDose1_manufacturer(String dose1_manufacturer) {
        this.dose1_manufacturer = dose1_manufacturer;
    }

    public String getDose2_batch() {
        return dose2_batch;
    }

    public void setDose2_batch(String dose2_batch) {
        this.dose2_batch = dose2_batch;
    }

    public String getDose2_date() {
        return dose2_date;
    }

    public void setDose2_date(String dose2_date) {
        this.dose2_date = dose2_date;
    }

    public String getDose2_facility() {
        return dose2_facility;
    }

    public void setDose2_facility(String dose2_facility) {
        this.dose2_facility = dose2_facility;
    }

    public String getDose2_manufacturer() {
        return dose2_manufacturer;
    }

    public void setDose2_manufacturer(String dose2_manufacturer) {
        this.dose2_manufacturer = dose2_manufacturer;
    }

    public String getUserIC() {
        return userIC;
    }

    public void setUserIC(String userIC) {
        this.userIC = userIC;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
