package br.dev.marco.mapper;

import br.dev.marco.domain.entity.Question;
import br.dev.marco.infra.repository.orm.QuestionAnswered;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class QuestionAnsweredAdapter {

    QuestionAnswered from(Question question, String answer, UUID requesterId) {
        return QuestionAnswered.builder()
                .questionId(UUID.randomUUID())
                .question(question.getMessage().getValue())
                .answer(answer)
                .answerCreatedAt(LocalDateTime.now())
                .requesterId(requesterId)
                .isPersistent(Boolean.FALSE)
                .build();
    }
}
