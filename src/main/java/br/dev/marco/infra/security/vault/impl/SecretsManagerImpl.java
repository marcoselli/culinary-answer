package br.dev.marco.infra.security.vault.impl;

import br.dev.marco.infra.security.vault.Vault;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

@ApplicationScoped
public class SecretsManagerImpl implements Vault {

    private final String VERSION_STAGE = "AWSCURRENT";
    @Inject
    SecretsManagerClient secretsManagerClient;

    @Override
    public String getSecret(String secretName) {
        return secretsManagerClient.getSecretValue(generateSecretRequest(secretName)).secretString();
    }
    private GetSecretValueRequest generateSecretRequest(String secretName) {
        return GetSecretValueRequest.builder()
                .secretId(secretName)
                .versionStage(VERSION_STAGE)
                .build();
    }

}