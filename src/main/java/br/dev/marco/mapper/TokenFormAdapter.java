package br.dev.marco.mapper;

import br.dev.marco.infra.web.request.LoginRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Form;

@ApplicationScoped
public class TokenFormAdapter {

   public Form from(LoginRequest loginRequest) {
        return new Form()
                .param("username", loginRequest.username())
                .param("password", loginRequest.password())
                .param("grant_type", "password");
    }
}
