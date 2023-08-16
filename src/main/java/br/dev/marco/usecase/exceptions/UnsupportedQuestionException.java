package br.dev.marco.usecase.exceptions;

import lombok.Getter;

@Getter
public class UnsupportedQuestionException extends Exception{
    private final String errorMessage =  "This question does not refer to culinary topics";
    public UnsupportedQuestionException() {
    }
}
