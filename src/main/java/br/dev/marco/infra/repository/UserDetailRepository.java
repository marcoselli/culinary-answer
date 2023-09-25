package br.dev.marco.infra.repository;

import br.dev.marco.infra.repository.orm.UserDetailORM;
import io.smallrye.mutiny.Uni;

public interface UserDetailRepository {
    Uni<Void> add(UserDetailORM userDetailORM);

    Uni<Void> update(UserDetailORM userDetailORM);

    Uni<UserDetailORM> getById(String username);

    Uni<UserDetailORM> getByUsername(String username);
}
