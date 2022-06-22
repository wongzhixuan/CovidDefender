package com.example.coviddefender.db.history;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryRepository {
    String TAG = "HistoryRepository";
    LiveData<List<History>> historyList;

    // Firebase Authentication
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private String userId;
    // Firebase Firestore
    private FirebaseFirestore firestore;
    private DocumentReference docRef;

    public HistoryRepository(Application application){
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firestore.setFirestoreSettings(new FirebaseFirestoreSettings.Builder().build());
        currentUser = auth.getCurrentUser();
        userId = currentUser.getUid();
        //docRef = firestore.collection("history").document(userId);
        docRef = firestore.collection("history").document("testing");
    }
    public void updateHistoryList(History history) {
        Map<String, Object> historyItem = new HashMap<>();
        historyItem.put("IsCheckOut", history.IsCheckOut);
        historyItem.put("location", history.location);
        historyItem.put("time", history.time);
        docRef.update("history", FieldValue.arrayUnion(historyItem))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "History Item successfully added in Firebase");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Update Successfully: " + e);
                    }
                });

    }
    public LiveData<List<History>> getAllHistory(){
        List<History> allHistory = null;

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    List<Map> historys ;
                    historys = (List<Map>) documentSnapshot.get("history");
                    for (Map history: historys){
                        allHistory.add(new History( history.get("IsCheckOut").toString(), history.get("location").toString(), history.get("time").toString()));
                    }
                }
                else{
                    Log.d(TAG, "Document Not Exist!");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Failed to get Data: "+e);
            }
        });
        historyList = (LiveData<List<History>>) allHistory;
        return historyList;
    }
}
