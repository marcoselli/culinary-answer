package br.dev.marco.usecase.enuns;

import lombok.Getter;

@Getter
public enum QuestionType {
    UNSUPPORTED_QUESTION("Unsupported question");

    private String description;

    QuestionType(String description) {
        this.description = description;
    }
}
