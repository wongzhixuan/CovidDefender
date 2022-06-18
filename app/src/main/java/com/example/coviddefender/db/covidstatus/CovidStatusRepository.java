package com.example.coviddefender.db.covidstatus;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CovidStatusRepository {
    private CovidStatusDao covidStatusDao;
    LiveData<List<CovidStatus>> covidStatusList;
    private CovidStatusRoomDB db;

    public CovidStatusRepository(Application application){
        db = CovidStatusRoomDB.getInstance(application);
        covidStatusDao = db.covidStatusDao();
        covidStatusList = covidStatusDao.getAllCovidStatus();

    }

    public void insert(CovidStatus covidStatus){
        CovidStatusRoomDB.databaseWriteExecutor.execute(() -> {
            covidStatusDao.insert(covidStatus);
        });
    }

    LiveData<List<CovidStatus>> getAllCovidStatus()
    {
        return this.covidStatusList;
    }
}
