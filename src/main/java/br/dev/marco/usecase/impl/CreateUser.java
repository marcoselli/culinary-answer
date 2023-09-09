package br.dev.marco.usecase.impl;

import br.dev.marco.domain.entity.User;
import br.dev.marco.infra.security.SecurityInfra;
import br.dev.marco.mapper.UserInfraAdapter;
import br.dev.marco.usecase.Command;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Named("createUser")
public class CreateUser implements Command<User,Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUser.class);

    @Inject
    SecurityInfra securityInfra;

    @Inject
    UserInfraAdapter userInfraAdapter;

    @Override
    public Uni<Void> execute(User user) {
        LOGGER.info("Username: {} - Starting user creation.", user.getUsername().getValue());
        return securityInfra.createUser(userInfraAdapter.from(user))
                .invoke(() -> LOGGER.info("Username: {} - Created successfully.", user.getUsername().getValue()))
                .onFailure()
                .invoke(err -> LOGGER.error("Username: {} - Failed to create user.", user.getUsername().getValue()));
    }

}
