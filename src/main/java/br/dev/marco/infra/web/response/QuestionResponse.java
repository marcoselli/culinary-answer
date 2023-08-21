package br.dev.marco.infra.web.response;


import lombok.Builder;

@Builder
public record QuestionResponse(String message) {
}
