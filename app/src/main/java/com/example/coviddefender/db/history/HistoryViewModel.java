package com.example.coviddefender.db.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coviddefender.entity.History;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    String TAG = "HistoryViewModel";
    private HistoryRepository historyRepository;
    private LiveData<List<History>> historyList;


    public HistoryViewModel(@NonNull Application application) {
        super(application);
        historyRepository = new HistoryRepository(application);
        historyList = historyRepository.getAllHistory();
    }
    public void update(History history){
        historyRepository.updateHistoryList(history);
    }

    public LiveData<List<History>> getAllHistory(){
        return this.historyList;
    }


}
