package br.dev.marco.domain.entity;

import br.dev.marco.domain.exception.RandomnessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RandomnessTest {

    @Test
    @DisplayName("Should instantiate value object Randomness correctly")
    void instantiateRandomness() throws RandomnessException {
        var randomness = new Randomness(0.6);
        Assertions.assertNotNull(randomness);
        Assertions.assertEquals(0.6, randomness.getValue());
    }

    @Test
    @DisplayName("Should instantiate value object Randomness correctly when null")
    void instantiateNullRandomness() throws RandomnessException {
        var randomness = new Randomness(null);
        Assertions.assertNotNull(randomness);
        Assertions.assertEquals(0, randomness.getValue());
    }

    @Test
    @DisplayName("Should throw an exception when trying to instantiate Randomness lower than 0")
    void shouldThrowErrorWhenRandomnessLowerThanZero() {
        Assertions.assertThrows(RandomnessException.class, () -> new Randomness(-0.5));
    }

    @Test
    @DisplayName("Should throw an exception when trying to instantiate Randomness higher than 1")
    void shouldThrowErrorWhenRandomnessHigherThanOne() {
        Assertions.assertThrows(RandomnessException.class, () -> new Randomness(1.3));
    }
}
