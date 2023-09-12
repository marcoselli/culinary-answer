package br.dev.marco.usecase.impl;

import br.dev.marco.domain.entity.User;
import br.dev.marco.domain.exception.PasswordException;
import br.dev.marco.domain.exception.UsernameException;
import br.dev.marco.infra.security.SecurityInfra;
import br.dev.marco.infra.security.request.UserInfra;
import br.dev.marco.mapper.UserInfraAdapter;
import br.dev.marco.usecase.exceptions.UserCreationException;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

class CreateUserTest {
    @Mock
    SecurityInfra securityInfra;
    @Mock
    UserInfraAdapter userInfraAdapter;
    @InjectMocks
    CreateUser createUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute() throws PasswordException, UsernameException, UserCreationException {
        when(userInfraAdapter.from(any())).thenReturn(new UserInfra("testUser", "Test@123", "simple-user"));
        when(securityInfra.createUser(any())).thenReturn(Uni.createFrom().voidItem());
        var user = new User("testUser", "Test@123", "simple-user");
        createUser.execute(user)
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertCompleted();
    }
}

