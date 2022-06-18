package com.example.coviddefender.db.covidstatus;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CovidStatusViewModel extends AndroidViewModel {
    private CovidStatusRepository repository;
    private LiveData<List<CovidStatus>> covidstatusList;


    public CovidStatusViewModel(@NonNull Application application) {
        super(application);
        repository = new CovidStatusRepository(application);
        covidstatusList = repository.getAllCovidStatus();
    }

    public void insert(CovidStatus covidStatus){
        repository.insert(covidStatus);
    }
    public LiveData<List<CovidStatus>> getAllCovidStatus() { return this.covidstatusList;}
}
