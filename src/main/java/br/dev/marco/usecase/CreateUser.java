package br.dev.marco.usecase;

import br.dev.marco.domain.User;
import br.dev.marco.infra.security.SecurityInfra;
import br.dev.marco.mapper.UserInfraMapper;
import br.dev.marco.usecase.enuns.UserType;
import br.dev.marco.usecase.exceptions.UserCreationException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CreateUser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUser.class);

    @Inject
    SecurityInfra securityInfra;

    @Inject
    UserInfraMapper userInfraMapper;

    public Uni<Void> execute(User user, String userType) {
        LOGGER.info("Username: {} - Starting user creation.", user.getUsername().getValue());
        if(userType.equals(UserType.FREE.getRoleName())) return createFreeUser(user);
        if(userType.equals(UserType.MEMBER.getRoleName())) return createMemberUser(user);
        return Uni.createFrom().failure(new UserCreationException("Invalid user type"));
    }

    private Uni<Void> createFreeUser(User user) {
        user.assignFreeUser();
        return securityInfra.createUser(userInfraMapper.from(user));
    }

    private Uni<Void> createMemberUser(User user) {
        user.assignMemberUser();
        return securityInfra.createUser(userInfraMapper.from(user));
    }


}
