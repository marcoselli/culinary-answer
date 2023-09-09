package br.dev.marco.domain.entity;

import br.dev.marco.domain.entity.Username;
import br.dev.marco.domain.exception.UsernameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UsernameTest {

    @Test
    @DisplayName("Should instantiate the value object Username correctly")
    void instantiateUsernameCorrectly() throws UsernameException {
        var username = new Username("johndoe");
        Assertions.assertNotNull(username);
        Assertions.assertEquals("johndoe", username.getValue());
    }

    @Test
    @DisplayName("Should throw an exception when trying to instantiate a Username with length lower than 4 characters")
    void shouldThrowErrorWhenUsernameLengthLowerThan4Characters() {
        Assertions.assertThrows(UsernameException.class, () -> new Username("joe"));
    }

    @Test
    @DisplayName("Should throw an exception when trying to instantiate a Username with length higher than 12 characters")
    void shouldThrowErrorWhenUsernameLengthHigherThan12Characters() {
        Assertions.assertThrows(UsernameException.class, () -> new Username("thisusernameistoolong"));
    }

    @Test
    @DisplayName("Should throw an exception when trying to instantiate a Username with special characters")
    void shouldThrowErrorWhenUsernameHasSpecialCharacters() {
        Assertions.assertThrows(UsernameException.class, () -> new Username("WithSpecialCh@racter"));
    }
}

