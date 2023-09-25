package br.dev.marco.domain.entity;

import br.dev.marco.domain.exceptions.PasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordTest {

    @Test
    @DisplayName("Should instantiate value object Password correctly")
    void instantiatePasswordCorrecty() throws PasswordException {
        var password = new Password("Test@123");
        Assertions.assertNotNull(password);
        Assertions.assertEquals("Test@123", password.getValue());
    }

    @Test
    @DisplayName("Should throw an exception when Password length lower than 8 characters")
    void shouldThrowErrorWhenPasswordLengthLowerThan8Characters() {
        Assertions.assertThrows(PasswordException.class, () -> new Password("Te@12"));
    }

    @Test
    @DisplayName("Should throw an exception when Password length higher than 16 characters")
    void shouldThrowErrorWhenPasswordLengtHigherThan16Characters() {
        Assertions.assertThrows(PasswordException.class, () -> new Password("ThisUsernameTooH!gher123"));
    }

    @Test
    @DisplayName("Should throw an exception when trying to instantiate a Password without special characters")
    void shouldThrowErrorWhenPasswordDontHaveSpecialCharacter() {
        Assertions.assertThrows(PasswordException.class, () -> new Password("Common123"));
    }

    @Test
    @DisplayName("Should throw an exception when trying to instantiate a Password without number")
    void shouldThrowErrorWhenPasswordDontHaveNumber() {
        Assertions.assertThrows(PasswordException.class, () -> new Password("CommonP@ss"));
    }

    @Test
    @DisplayName("Should throw an exception when trying to instantiate a Password without capital letter")
    void shouldThrowErrorWhenPasswordDontCapitalLetter() {
        Assertions.assertThrows(PasswordException.class, () -> new Password("common@123"));
    }
}
