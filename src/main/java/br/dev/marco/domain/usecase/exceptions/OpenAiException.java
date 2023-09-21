package br.dev.marco.domain.usecase.exceptions;

import lombok.Getter;

@Getter
public class OpenAiException extends Exception {
    private String errorMessage;


    private String rootCause;
    public OpenAiException(Throwable throwable) {
        super(throwable);
        this.rootCause = throwable.getClass().getSimpleName();
        if(throwable instanceof RuntimeException) this.errorMessage = "OpenAI service connection timed out";
        else this.errorMessage = "An unexpected error occurred with openai services";
    }
}
