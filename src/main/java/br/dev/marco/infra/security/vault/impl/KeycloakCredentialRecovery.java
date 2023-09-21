package br.dev.marco.infra.security.vault.impl;

import br.dev.marco.config.env.SecretsConfig;
import br.dev.marco.infra.security.vault.Vault;
import br.dev.marco.infra.security.vault.VaultRecoveryManager;
import br.dev.marco.infra.security.vault.exceptions.CredentialException;
import br.dev.marco.infra.security.vault.model.KeycloakSecret;
import br.dev.marco.util.JsonUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class KeycloakCredentialRecovery extends JsonUtil<KeycloakSecret> implements VaultRecoveryManager<KeycloakSecret> {

    @Inject
    SecretsConfig secretsConfig;
    @Inject
    Vault vault;

    public KeycloakCredentialRecovery() {
        super(KeycloakSecret.class);
    }

    @Override
    public KeycloakSecret secrets() throws CredentialException {
        String jsonSecret = vault.getSecret(secretsConfig.keycloakSecretName());
        return this.jsonStringToObject(jsonSecret)
                .orElseThrow(() -> new CredentialException("Fail to recover credentials from Keycloak"));
    }
}
