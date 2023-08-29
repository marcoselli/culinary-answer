package br.dev.marco.mapper;

import br.dev.marco.domain.User;
import br.dev.marco.infra.security.request.UserInfra;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserInfraMapper {
    UserInfra from(User user);
}
