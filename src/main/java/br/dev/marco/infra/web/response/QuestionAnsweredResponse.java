package br.dev.marco.infra.web.response;

import br.dev.marco.domain.usecase.enuns.Topic;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record QuestionAnsweredResponse(
        UUID questionId,
        Topic topic,
        String tittle,
        String question,
        String answer,
        LocalDateTime answerCreatedAt) {
}
