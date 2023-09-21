package br.dev.marco.infra.security.sso.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class KeycloakErrorMessage {
    private String errorMessage;
}
