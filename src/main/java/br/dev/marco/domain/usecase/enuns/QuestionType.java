package br.dev.marco.domain.usecase.enuns;

import lombok.Getter;

@Getter
public enum QuestionType {
    UNSUPPORTED_QUESTION("Unsupported question");

    private String description;

    QuestionType(String description) {
        this.description = description;
    }
}
