package br.dev.marco.domain.entity;

import br.dev.marco.domain.exceptions.MessageException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MessageTest {

    @Test
    @DisplayName("Should instantiate the value object Message correctly")
    void instantiateMessageCorrectly() throws MessageException {
        var message = new Message("This is a test message!");
        Assertions.assertNotNull(message);
        Assertions.assertEquals("This is a test message!", message.getValue());
    }

    @Test
    @DisplayName("Should throw an exception when the message is null")
    void shouldThrowErrorWhenMessageIsNull()  {
        Assertions.assertThrows(MessageException.class, () -> new Message(null));
    }

    @Test
    @DisplayName("Should throw an exception when the message is empty")
    void shouldThrowErrorWhenMessageIsEmpty() {
        Assertions.assertThrows(MessageException.class, () -> new Message(""));
    }

    @Test
    @DisplayName("Should throw an exception when the message is blank")
    void shouldThrowErrorWhenMessageIsBlank() {
        Assertions.assertThrows(MessageException.class, () -> new Message(" "));
    }

    @Test
    @DisplayName("Should throw an exception when the message length is higher than 280 characters")
    void shouldThrowErrorWhenMessageLengthIsHigherThan280Characters() {
        String message = "Qual é a receita de um prato tradicional de paella espanhola, incluindo os ingredientes essenciais e os passos fundamentais para prepará-lo em casa? " +
                "Gostaria de surpreender meus amigos com essa deliciosa iguaria espanhola." +
                "Lembrar de adicionar o passo a passo exato para que eu possa fazer esse prato de uma forma simples e rápida";
        Assertions.assertThrows(MessageException.class, () -> new Message(message));
    }
}

