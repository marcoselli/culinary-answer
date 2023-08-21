package br.dev.marco.mapper;


import br.dev.marco.domain.FreeUser;
import br.dev.marco.infra.web.request.CreateUserRequest;

public class FreeUserMapper {
    public static FreeUser from(CreateUserRequest createUserRequest) {
        return new FreeUser(createUserRequest.username(), createUserRequest.password());
    }
}
