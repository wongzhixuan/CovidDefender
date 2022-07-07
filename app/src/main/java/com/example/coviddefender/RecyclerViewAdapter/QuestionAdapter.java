package com.example.coviddefender.RecyclerViewAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coviddefender.R;
import com.example.coviddefender.entity.AnswerSelected;
import com.example.coviddefender.entity.Question;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class QuestionAdapter extends FirestoreRecyclerAdapter<Question, QuestionAdapter.QuestionViewHolder> {
    int lastPos = -1;
    int questionId = 0;
    private final ArrayList<AnswerSelected> selected = new ArrayList<AnswerSelected>();

    public QuestionAdapter(@NonNull FirestoreRecyclerOptions<Question> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuestionAdapter.QuestionViewHolder holder, int position, @NonNull Question model) {
        // bind data with view
        holder.tv_question_title.setText(model.getQuestion());
        questionId = model.getId();
        holder.btn_answer_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = 0;
                Boolean hasDuplicate = false;
                holder.btn_answer_yes.setChecked(true);
                holder.btn_answer_no.setChecked(false);
                for (int i = 0; i < selected.size(); i ++) {
                    AnswerSelected ans = selected.get(i);
                    if(ans.getPosition() == model.getId()){
                        hasDuplicate = true;
                        selected.set(i, new AnswerSelected(model.getId(),true, "yes"));
                    }
                }
                if(hasDuplicate == false){
                    selected.add(new AnswerSelected(model.getId(),true, "yes"));
                }


            }
        });
        holder.btn_answer_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean hasDuplicate = false;

                holder.btn_answer_yes.setChecked(false);
                holder.btn_answer_no.setChecked(true);
                for (int i = 0; i < selected.size(); i ++) {
                    AnswerSelected ans = selected.get(i);
                    if(ans.getPosition() == model.getId()){
                        hasDuplicate = true;
                        selected.set(i, new AnswerSelected(model.getId(),true, "no"));

                    }
                }
                if(hasDuplicate == false){
                    selected.add(new AnswerSelected(model.getId(),true, "no"));
                }
            }
        });

    }

    @NonNull
    @Override
    public QuestionAdapter.QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create new view, defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questions_item, parent, false);

        return new QuestionViewHolder(view);
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // setting animation
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        Log.d("question_adapter", "getItemCount: " + super.getItemCount());
        return super.getItemCount();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_question_title;
        private final MaterialButton btn_answer_no, btn_answer_yes;

        public QuestionViewHolder(View view) {
            super(view);
            // initialize variables
            tv_question_title = view.findViewById(R.id.tv_question_title);
            btn_answer_no = view.findViewById(R.id.btn_answer_no);
            btn_answer_yes = view.findViewById(R.id.btn_answer_yes);
        }

    }
    public ArrayList<AnswerSelected> getSelected() {
        return selected;
    }


}
