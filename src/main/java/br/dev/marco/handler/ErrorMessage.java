package br.dev.marco.handler;

import br.dev.marco.usecase.exceptions.OpenAiException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {
    private String errorType;
    private String rootCause;
    private String description;

    public ErrorMessage(OpenAiException exception) {
        this.errorType = exception.getClass().getSimpleName();
        this.description = exception.getErrorMessage();
        this.rootCause = exception.getRootCause();
    }


}
