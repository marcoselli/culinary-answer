package br.dev.marco.mapper;

import br.dev.marco.domain.entity.User;
import br.dev.marco.infra.security.sso.request.UserInfra;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserInfraAdapter {
    public UserInfra from(User user) {
        return UserInfra.builder()
                .username(user.getUsername().getValue())
                .password(user.getPassword().getValue())
                .group(user.getUserType().getGroupName())
                .build();
    }
}
