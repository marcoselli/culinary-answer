package br.dev.marco.infra.security.vault.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class KeycloakSecret {
    private String realmName;
    private String adminClientUsername;
    private String adminClientPassword;
}
