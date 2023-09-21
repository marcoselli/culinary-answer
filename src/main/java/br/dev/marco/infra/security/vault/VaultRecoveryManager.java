package br.dev.marco.infra.security.vault;

import br.dev.marco.infra.security.vault.exceptions.CredentialException;


public interface VaultRecoveryManager<T> {
    T secrets() throws CredentialException;
}
