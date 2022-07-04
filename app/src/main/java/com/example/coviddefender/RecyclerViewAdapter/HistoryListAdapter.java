package com.example.coviddefender.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coviddefender.R;
import com.example.coviddefender.db.history.HistoryModal;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {
    int lastPos = -1;
    // create variables for list, context, interface
    private ArrayList<HistoryModal> historyList;
    private Context context;
    private HistoryClickInterface historyClickInterface;

    // create constructor
    public HistoryListAdapter(ArrayList<HistoryModal> historyList, Context context, HistoryClickInterface historyClickInterface) {
        this.historyList = historyList;
        this.context = context;
        this.historyClickInterface = historyClickInterface;
    }

    @Override
    public HistoryListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return HistoryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.HistoryViewHolder holder, int position) {
        // setting data to our recycler view item
        HistoryModal historyModal = historyList.get(position);
        holder.tv_location_name.setText(historyModal.getLocation());
        holder.tv_checkin_time.setText(historyModal.getTime());
        if (historyModal.getIsCheckOut() == "true") {
            holder.btn_check_out.setEnabled(false);
        } else {
            holder.btn_check_out.setEnabled(true);
        }

        setAnimation(holder.itemView, position);
        holder.btn_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyClickInterface.onHistoryClick(position);
            }
        });

    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_checkin_time;
        private final TextView tv_location_name;
        private final MaterialButton btn_check_out;

        private HistoryViewHolder(View itemView) {
            super(itemView);
            // initialize variables
            tv_checkin_time = itemView.findViewById(R.id.tv_checkin_time);
            tv_location_name = itemView.findViewById(R.id.tv_location_name);
            btn_check_out = itemView.findViewById(R.id.btn_check_out);

        }

        static HistoryListAdapter.HistoryViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_item, parent, false);
            return new HistoryViewHolder(view);
        }

    }

    // creating a interface for on click
    public interface HistoryClickInterface {
        void onHistoryClick(int position);
    }


}
