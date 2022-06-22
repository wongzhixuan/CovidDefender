package com.example.coviddefender.RecyclerViewAdapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coviddefender.R;
import com.example.coviddefender.db.history.History;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class HistoryListAdapter extends ListAdapter<History, HistoryListAdapter.HistoryViewHolder> {
    List<History> historyList;

    public HistoryListAdapter(@NonNull DiffUtil.ItemCallback<History> diffCallback) {
        super(diffCallback);
    }

    protected HistoryListAdapter(@NonNull AsyncDifferConfig<History> config) {
        super(config);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return HistoryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.HistoryViewHolder holder, int position) {
        History current = getItem(position);
        holder.bind(current.getLocation(), current.getTime(), current.getCheckOut());
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_checkin_time;
        private final TextView tv_location_name;
        private final MaterialButton btn_check_out;

        private HistoryViewHolder(View itemView) {
            super(itemView);
            tv_checkin_time = itemView.findViewById(R.id.tv_checkin_time);
            tv_location_name = itemView.findViewById(R.id.tv_location_name);
            btn_check_out = itemView.findViewById(R.id.btn_check_out);

        }

        static HistoryViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_item, parent, false);
            return new HistoryViewHolder(view);
        }

        public void bind(String location, String time, String IsCheckOut) {
            tv_location_name.setText(location);
            tv_checkin_time.setText(time);
            if (IsCheckOut == "true") {
                btn_check_out.setStrokeColorResource(R.color.dim_grey_light);
                btn_check_out.setTextColor(Color.GRAY);
            } else {

            }
        }
    }

    public static class HistoryDiff extends DiffUtil.ItemCallback<History> {

        @Override
        public boolean areItemsTheSame(@NonNull History oldItem, @NonNull History newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull History oldItem, @NonNull History newItem) {
            return oldItem.getTime().equals(newItem.getTime()) &&
                    oldItem.getLocation().equals(newItem.getLocation()) &&
                    oldItem.getCheckOut().equals(newItem.getCheckOut());
        }
    }
}
