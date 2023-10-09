package br.dev.marco.domain.entity;

import br.dev.marco.domain.usecase.enuns.Topic;

import java.time.LocalDateTime;
public class QuestionAnswered {
    private String questionId;
    private Topic topic;
    private String tittle;
    private String question;
    private String answer;
    private LocalDateTime answerCreatedAt;
    private String requesterId;
    private Boolean isFavorite;


    public void favoriteQuestion(String topic, String tittle) {
        this.isFavorite = Boolean.TRUE;
        this.topic = Topic.valueOf(topic);
        this.tittle = tittle;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public LocalDateTime getAnswerCreatedAt() {
        return answerCreatedAt;
    }

    public void setAnswerCreatedAt(LocalDateTime answerCreatedAt) {
        this.answerCreatedAt = answerCreatedAt;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
