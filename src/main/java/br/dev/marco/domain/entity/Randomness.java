package br.dev.marco.domain.entity;

import br.dev.marco.domain.exceptions.RandomnessException;
import lombok.SneakyThrows;

import java.util.Objects;

public class Randomness {

    private Double value;
    @SneakyThrows
    public Randomness(Double randomness) throws RandomnessException {
        validate(randomness);
        this.value = Objects.isNull(randomness) ? 0 : randomness;
    }

    private void validate(Double randomness) throws RandomnessException {
        if (Objects.nonNull(randomness)) {
            if (randomness < 0 || randomness > 1)
                throw new RandomnessException("The randomness value must be between 0 and 1");
        }
    }

    public Double getValue() {
        return this.value;
    }
}
