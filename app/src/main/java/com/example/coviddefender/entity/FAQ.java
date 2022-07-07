package com.example.coviddefender.entity;

public class FAQ {
    String faq_question;
    String faq_answer;

    public FAQ(){

    }

    public FAQ(String faq_question, String faq_answer){
        this.faq_question = faq_question;
        this.faq_answer = faq_answer;
    }

    public String getFaq_answer() {
        return faq_answer;
    }

    public String getFaq_question() {
        return faq_question;
    }
}
