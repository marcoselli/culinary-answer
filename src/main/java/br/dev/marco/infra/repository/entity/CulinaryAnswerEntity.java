package br.dev.marco.infra.repository.entity;

import java.time.LocalDateTime;

public class CulinaryAnswerEntity {

    private String questionId;
    private String subject;
    private String question;
    private String answer;
    private LocalDateTime answerCreatedAt;
    private String answerCreatedByUser;
    private Boolean isPersistent;

}
