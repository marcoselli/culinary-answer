package br.dev.marco.domain.usecase.impl;

import br.dev.marco.domain.entity.UserDetail;
import br.dev.marco.infra.repository.UserDetailRepository;
import br.dev.marco.infra.security.sso.SSO;
import br.dev.marco.infra.security.vault.exceptions.CredentialException;
import br.dev.marco.mapper.UserDetailMapper;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RetrieveUserDetailFromToken {

    private final Logger LOGGER = LoggerFactory.getLogger(RetrieveUserDetailFromToken.class);
    @Inject
    JsonWebToken jsonWebToken;
    @Inject
    UserDetailRepository userDetailRepository;
    @Inject
    UserDetailMapper userDetailMapper;

    @Blocking
    public Uni<UserDetail> execute() {
        var username = (String) jsonWebToken.getClaim("preferred_username");
        LOGGER.info("Username {} - Starting getting user detail", username);
        return userDetailRepository.getByUsername(username)
                .map(userDetailORM -> userDetailMapper.from(userDetailORM))
                .invoke(() -> LOGGER.info("Username {} - Success getting user detail", username))
                .onFailure()
                .invoke(err -> LOGGER.error("Username {} - Error getting user detail. Fail with: ", username, err));
    }
}
