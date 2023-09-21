package br.dev.marco.infra.security.vault;

public interface Vault {
    String getSecret(String secretName);
}
