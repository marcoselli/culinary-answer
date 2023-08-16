package br.dev.marco.infra.web.request;


import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotEmpty;

public class QuestionRequest {
    @NotNull @NotEmpty
    public String message;
    public String randomness;
}
