package br.dev.marco.mapper;

import br.dev.marco.domain.entity.Question;
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
                .isPersistent(Boolean.FALSE)
                .build();
    }
}
