package com.example.coviddefender.RecyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coviddefender.R;
import com.example.coviddefender.entity.Announcement;
import com.example.coviddefender.entity.FAQ;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FAQAdapter extends FirestoreRecyclerAdapter<FAQ, FAQAdapter.FAQViewHolder> {

    public FAQAdapter(@NonNull FirestoreRecyclerOptions<FAQ> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FAQViewHolder holder, int position, @NonNull FAQ model) {
        holder.faq_question.setText(model.getFaq_question());
        holder.faq_answer.setText(model.getFaq_answer());
    }

    @NonNull
    @Override
    public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_item, parent, false);
        return new FAQAdapter.FAQViewHolder(view);
    }

    public class FAQViewHolder extends RecyclerView.ViewHolder {
        private final TextView faq_question;
        private final TextView faq_answer;
        public FAQViewHolder(View view) {
            super(view);
            faq_question = view.findViewById(R.id.faq_question);
            faq_answer = view.findViewById(R.id.faq_answer);
        }
    }
}
