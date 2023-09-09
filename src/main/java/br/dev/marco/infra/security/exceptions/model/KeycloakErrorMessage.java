package br.dev.marco.infra.security.exceptions.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class KeycloakErrorMessage {
    private String errorMessage;
}
