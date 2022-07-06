package com.example.coviddefender.RecyclerViewAdapter;

import static com.example.coviddefender.R.id;
import static com.example.coviddefender.R.layout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coviddefender.R;
import com.example.coviddefender.entity.History;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class HistoryListAdapter extends FirestoreRecyclerAdapter<History, HistoryListAdapter.HistoryViewHolder> {
    int lastPos = -1;
    // Firestore
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;

    // Firebase Authentication
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String userId;

    String docId;

    // constructor
    public HistoryListAdapter(@NonNull FirestoreRecyclerOptions<History> options) {
        super(options);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
//        userId = currentUser.getUid();
        userId = "testing";
        firestore = FirebaseFirestore.getInstance();
        documentReference = firestore.collection("history").document(userId);
    }

    @Override
    protected void onBindViewHolder(@NonNull HistoryViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull History model) {
        // bind data with view
        holder.tv_location_name.setText(model.getLocation());
        Timestamp time = model.getTime();
        Date datetime = time.toDate();
        holder.tv_checkin_time.setText(String.valueOf(datetime));
        holder.btn_check_out.setEnabled(!model.getCheckOut().toString().equals("true"));

        setAnimation(holder.itemView, position);
        holder.btn_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth = FirebaseAuth.getInstance();
                currentUser = mAuth.getCurrentUser();
//        userId = currentUser.getUid();
                userId = "testing";
                firestore = FirebaseFirestore.getInstance();
                documentReference = firestore.collection("history").document(userId);
                documentReference.collection("historyItem").whereEqualTo("location",model.getLocation()).whereEqualTo("time",model.getTime())
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                        docId = documentSnapshot.getId();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("location", model.getLocation());
                                        bundle.putString("docId", docId);
                                        bundle.putString("time", model.getTime().toDate().toString());
                                        Navigation.findNavController(view).navigate(id.checkIn_Success,bundle);
                                    }
                                }
                            }
                        });

//                documentReference.collection("historyItem").document(docId).update("IsCheckOut", true).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        AlertDialog.Builder dialog= new AlertDialog.Builder(view.getContext());
//                        dialog.setCancelable(false);
//                        dialog.setTitle("Successfully Check Out");
//                        Timestamp time = Timestamp.now();
//                        Date curr_time = time.toDate();
//                        dialog.setMessage("Location: "+ model.getLocation() + "\nAt " + Timestamp.now().toDate().toString());
//                        dialog.show();
//                    }
//                }).addOnFailureListener(
//                        new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w("CheckOut", "Error updating document", e);
//                            }
//                        }
//                );


            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // setting animation
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }


    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_checkin_time;
        private final TextView tv_location_name;
        private final MaterialButton btn_check_out;


        private HistoryViewHolder(View itemView) {
            super(itemView);
            // initialize variables
            tv_checkin_time = itemView.findViewById(id.tv_checkin_time);
            tv_location_name = itemView.findViewById(id.tv_location_name);
            btn_check_out = itemView.findViewById(id.btn_check_out);
        }

    }



}
