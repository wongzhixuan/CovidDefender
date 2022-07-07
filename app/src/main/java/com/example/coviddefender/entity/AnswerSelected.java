package com.example.coviddefender.entity;

public class AnswerSelected {
    int position;
    Boolean isSelected;
    String answer;

    public AnswerSelected() {

    }

    public AnswerSelected(int position, Boolean isSelected, String answer) {
        this.answer = answer;
        this.isSelected = isSelected;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
