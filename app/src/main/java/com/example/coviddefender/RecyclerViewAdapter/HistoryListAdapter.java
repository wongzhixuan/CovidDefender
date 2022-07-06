package com.example.coviddefender.RecyclerViewAdapter;

import static com.example.coviddefender.R.id;
import static com.example.coviddefender.R.layout;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coviddefender.entity.History;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;

import java.util.Date;

public class HistoryListAdapter extends FirestoreRecyclerAdapter<History, HistoryListAdapter.HistoryViewHolder> {
    int lastPos = -1;
    // constructor
    public HistoryListAdapter(@NonNull FirestoreRecyclerOptions<History> options) {
        super(options);

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
