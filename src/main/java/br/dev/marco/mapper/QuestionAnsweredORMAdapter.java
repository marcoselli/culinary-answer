package br.dev.marco.mapper;

import br.dev.marco.domain.entity.Question;
import br.dev.marco.domain.entity.QuestionAnswered;
import br.dev.marco.domain.usecase.enuns.Topic;
import br.dev.marco.infra.repository.orm.QuestionAnsweredORM;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class QuestionAnsweredORMAdapter {

    public QuestionAnsweredORM from(Question question, String answer) {
        return QuestionAnsweredORM.builder()
                .questionId(UUID.randomUUID())
                .topic(Topic.GENERAL)
                .question(question.getMessage().getValue())
                .answer(answer)
                .answerCreatedAt(LocalDateTime.now())
                .requesterId(UUID.fromString(question.getRequester().getUserDetailId()))
                .isFavorite(Boolean.FALSE)
                .build();
    }

    public QuestionAnsweredORM from(QuestionAnswered questionAnswered) {
        return QuestionAnsweredORM.builder()
                .questionId(UUID.fromString(questionAnswered.getQuestionId()))
                .topic(questionAnswered.getTopic())
                .tittle(questionAnswered.getTittle())
                .question(questionAnswered.getQuestion())
                .answer(questionAnswered.getAnswer())
                .answerCreatedAt(questionAnswered.getAnswerCreatedAt())
                .isFavorite(questionAnswered.getIsFavorite())
                .requesterId(UUID.fromString(questionAnswered.getRequesterId()))
                .build();
    }
}
