package br.dev.marco.infra.security.sso.exceptions;

import br.dev.marco.infra.security.sso.exceptions.model.KeycloakErrorMessage;

public class SecurityException extends Throwable {
    public SecurityException(KeycloakErrorMessage message) {
        super(message.getErrorMessage());
    }

    public SecurityException(String message) {
        super(message);
    }
}
