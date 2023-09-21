package br.dev.marco.domain.usecase.exceptions;

public class UserCreationException extends Exception {
    public UserCreationException(String message) {
        super(message);
    }
}
