package br.dev.marco.infra.web.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class QuestionRequest {
    @NotNull @NotEmpty
    public String message;
    @Min(value = 0) @Max(value = 1)
    public Double randomness;
}
