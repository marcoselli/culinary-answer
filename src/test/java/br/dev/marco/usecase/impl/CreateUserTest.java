package br.dev.marco.usecase.impl;

import br.dev.marco.domain.entity.User;
import br.dev.marco.domain.exceptions.PasswordException;
import br.dev.marco.domain.exceptions.UsernameException;
import br.dev.marco.domain.usecase.enuns.UserType;
import br.dev.marco.domain.usecase.impl.CreateUser;
import br.dev.marco.infra.security.sso.SSO;
import br.dev.marco.infra.security.sso.request.UserInfra;
import br.dev.marco.infra.security.vault.exceptions.CredentialException;
import br.dev.marco.mapper.UserInfraAdapter;
import br.dev.marco.domain.usecase.exceptions.UserCreationException;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class CreateUserTest {
    @Mock
    SSO SSO;
    @Mock
    UserInfraAdapter userInfraAdapter;
    @InjectMocks
    CreateUser createUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //@Test
    void testExecute() throws PasswordException, UsernameException, UserCreationException, CredentialException {
        when(userInfraAdapter.from(any())).thenReturn(new UserInfra("testUser", "Test@123", "simple-user"));
//        when(SSO.createUser(any())).thenReturn(Uni.createFrom().voidItem());
//        var user = new User("testUser", "Test@123", UserType.FREE);
//        createUser.execute(user)
//                .subscribe()
//                .withSubscriber(UniAssertSubscriber.create())
//                .assertCompleted();
    }
}

