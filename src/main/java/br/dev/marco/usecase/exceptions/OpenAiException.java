package br.dev.marco.usecase.exceptions;

import lombok.Getter;

@Getter
public class OpenAiException extends Exception {
    private String errorMessage;
    public OpenAiException(Throwable throwable) {
        super(throwable);
        if(throwable instanceof RuntimeException)
            this.errorMessage = "OpenAI service connection timed out";
        else this.errorMessage = "An unexpected error occurred with openai services";
    }
}
