package br.dev.marco.infra.security.vault.impl;

import br.dev.marco.config.env.SecretsConfig;
import br.dev.marco.infra.security.vault.Vault;
import br.dev.marco.infra.security.vault.VaultRecoveryManager;
import br.dev.marco.infra.security.vault.exceptions.CredentialException;
import br.dev.marco.infra.security.vault.model.OpenAISecret;
import br.dev.marco.util.JsonUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OpenAICredentialRecovery extends JsonUtil<OpenAISecret> implements VaultRecoveryManager<OpenAISecret> {
    @Inject
    SecretsConfig secretsConfig;
    @Inject
    Vault vault;

    public OpenAICredentialRecovery() {
        super(OpenAISecret.class);
    }

    @Override
    public OpenAISecret secrets() throws CredentialException {
        String jsonSecret = vault.getSecret(secretsConfig.openAiSecretName());
        return this.jsonStringToObject(jsonSecret)
                .orElseThrow(() -> new CredentialException("Fail to recover credentials from OpenAISecret"));
    }
}
