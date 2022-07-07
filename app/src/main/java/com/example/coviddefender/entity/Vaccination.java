package com.example.coviddefender.entity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;

import java.util.Map;

public class Vaccination {
    @PropertyName("IsRegistered")
    Boolean IsRegistered;
    @PropertyName("registered_time")
    Timestamp registered_time;
    @PropertyName("eligible_for_vaccine")
    Timestamp eligible_for_vaccine;
    @PropertyName("dose1")
    Map<String, Object> dose1;
    @PropertyName("dose2")
    Map<String, Object> dose2;
    @PropertyName("booster")
    Map<String, Object> booster;
    @PropertyName("vaccine_status")
    String vaccine_status;

    public Vaccination() {
    }

    public Vaccination(
            Boolean IsRegistered,
            Timestamp registered,
            Timestamp eligible_for_vaccine,
            Map<String, Object> dose1,
            Map<String, Object> dose2,
            Map<String, Object> booster,
            String vaccine_status) {
        this.IsRegistered = IsRegistered;
        this.registered_time = registered;
        this.eligible_for_vaccine = eligible_for_vaccine;
        this.dose1 = dose1;
        this.dose2 = dose2;
        this.booster = booster;
        this.vaccine_status = vaccine_status;
    }

    public Boolean getIsRegistered() {
        return IsRegistered;
    }

    public Timestamp getRegistered_time() {
        return registered_time;
    }

    public Map<String, Object> getBooster() {
        return booster;
    }

    public Map<String, Object> getDose1() {
        return dose1;
    }

    public Map<String, Object> getDose2() {
        return dose2;
    }

    public Timestamp getEligible_for_vaccine() {
        return eligible_for_vaccine;
    }

    public String getVaccine_status() {
        return vaccine_status;
    }
}
