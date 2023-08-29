package br.dev.marco.mapper;


import br.dev.marco.domain.User;
import br.dev.marco.infra.web.request.CreateUserRequest;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User from(CreateUserRequest createUserRequest);
}
