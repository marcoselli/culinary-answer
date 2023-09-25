package br.dev.marco.mapper;

import br.dev.marco.domain.entity.UserDetail;
import br.dev.marco.infra.repository.orm.UserDetailORM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserDetailMapper {

    UserDetail from(UserDetailORM userDetailORM);
}
