package com.example.coviddefender.db.history;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
