package br.dev.marco.domain.usecase;

import br.dev.marco.infra.security.vault.exceptions.CredentialException;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

import java.rmi.NoSuchObjectException;

public interface Command<T,V> {
    Uni<V> execute(T t) throws NoSuchObjectException, NoSuchFieldException, CredentialException;
}
