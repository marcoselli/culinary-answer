package br.dev.marco.domain.entity;

import br.dev.marco.domain.exception.MessageException;

import java.util.Objects;

public class Message {

    private final Integer MAXIMUM_MESSAGE_LENGHT = 280;

    private String value;

    public Message(String message) throws MessageException {
        validate(message);
        this.value = message;
    }

    private void validate(String message) throws MessageException {
        if(Objects.isNull(message) || message.isEmpty() || message.isBlank()) throw new MessageException("The message must be filled");
        if(message.length() > MAXIMUM_MESSAGE_LENGHT) throw new MessageException("The message must not exceed the maximum length of 280 characters");
    }

    public String getValue() {
        return value;
    }
}
