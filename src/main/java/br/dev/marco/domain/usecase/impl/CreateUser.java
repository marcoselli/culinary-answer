package br.dev.marco.domain.usecase.impl;

import br.dev.marco.domain.entity.User;
import br.dev.marco.infra.repository.UserDetailRepository;
import br.dev.marco.infra.security.sso.SSO;
import br.dev.marco.infra.security.vault.exceptions.CredentialException;
import br.dev.marco.mapper.UserDetailORMAdapter;
import br.dev.marco.mapper.UserInfraAdapter;
import br.dev.marco.domain.usecase.Command;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Named("createUser")
public class CreateUser {

    private final Logger LOGGER = LoggerFactory.getLogger(CreateUser.class);
    @Inject
    SSO sso;
    @Inject
    UserInfraAdapter userInfraAdapter;
    @Inject
    UserDetailRepository userDetailRepository;
    @Inject
    UserDetailORMAdapter userDetailORMAdapter;


    public Uni<Void> execute(User user) throws CredentialException {
        LOGGER.info("Username: {} - Starting user creation.", user.getUsername().getValue());
        return sso.createUser(userInfraAdapter.from(user))
                .flatMap(ignored -> sso.getUserRepresentationByUsername(user.getUsername().getValue()))
                .flatMap(ssoUser -> userDetailRepository.add(
                        userDetailORMAdapter.from(user,ssoUser.getId())
                ))
                .invoke(() -> LOGGER.info("Username: {} - Created successfully.", user.getUsername().getValue()))
                .onFailure()
                .invoke(err -> LOGGER.error("Username: {} - Failed to create user.", user.getUsername().getValue()));
    }

}
