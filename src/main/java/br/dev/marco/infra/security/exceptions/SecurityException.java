package br.dev.marco.infra.security.exceptions;

import br.dev.marco.infra.security.exceptions.model.KeycloakErrorMessage;

public class SecurityException extends Throwable {

    public SecurityException(KeycloakErrorMessage message) {
        super(message.getErrorMessage());
    }
}
