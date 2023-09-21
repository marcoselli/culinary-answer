package br.dev.marco.mapper;


import br.dev.marco.domain.entity.User;
import br.dev.marco.domain.exception.PasswordException;
import br.dev.marco.domain.exception.UsernameException;
import br.dev.marco.infra.web.request.CreateUserRequest;
import br.dev.marco.domain.usecase.exceptions.UserCreationException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserAdapter {
    public User from(CreateUserRequest createUserRequest, String userType) throws PasswordException, UsernameException, UserCreationException {
        return new User(createUserRequest.username(), createUserRequest.password(), userType);
    }
}
