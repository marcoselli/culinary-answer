package br.dev.marco.mapper;

import br.dev.marco.domain.User;
import br.dev.marco.infra.security.request.UserInfra;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserInfraAdapter {
    public UserInfra from(User user) {
        return UserInfra.builder()
                .username(user.getUsername().getValue())
                .password(user.getPassword().getValue())
                .role(user.getRole())
                .build();
    }
}
