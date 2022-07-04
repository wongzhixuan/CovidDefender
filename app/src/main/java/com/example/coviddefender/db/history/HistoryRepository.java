package com.example.coviddefender.db.history;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.coviddefender.entity.History;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class HistoryRepository {
    // Firebase Authentication
    private final FirebaseAuth auth;
    private final FirebaseUser currentUser;
    private final String userId;
    // Firebase Firestore
    private final FirebaseFirestore firestore;
    private final DocumentReference docRef;
    String TAG = "HistoryRepository";
    LiveData<List<History>> historyList;

    public HistoryRepository(Application application) {
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

    public LiveData<List<History>> getAllHistory() {
        List<History> allHistory = null;

        docRef.collection("historyItem").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshots = task.getResult();
                            querySnapshots.forEach(new Consumer<QueryDocumentSnapshot>() {
                                @Override
                                public void accept(QueryDocumentSnapshot queryDocumentSnapshot) {
                                    allHistory.add(
                                            new History(
                                                    queryDocumentSnapshot.get("IsCheckOut").toString(),
                                                    queryDocumentSnapshot.get("location").toString(),
                                                    queryDocumentSnapshot.get("time").toString())
                                    );
                                    Log.d("HistoryList", queryDocumentSnapshot.getData().toString());
                                }
                            });
                        } else {
                            Log.d("HistoryList", task.getException().toString());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, e.getMessage().toString());
                                            }
                                        }
                );
        historyList = (LiveData<List<History>>) allHistory;
        return historyList;
    }
}
