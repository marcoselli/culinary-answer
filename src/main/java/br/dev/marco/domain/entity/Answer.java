package br.dev.marco.domain.entity;

import br.dev.marco.domain.usecase.enuns.QuestionType;
import br.dev.marco.domain.usecase.exceptions.UnsupportedQuestionException;

public class Answer {
    private String answer;

    public Answer(String answer) throws UnsupportedQuestionException {
        validate(answer);
        this.answer = answer;
    }

    private void validate(String answer) throws UnsupportedQuestionException {
        if(answer.contains(QuestionType.UNSUPPORTED_QUESTION.name()))
            throw new UnsupportedQuestionException();
    }
}
