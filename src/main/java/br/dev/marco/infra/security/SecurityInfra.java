package br.dev.marco.infra.security;

import br.dev.marco.infra.security.request.UserInfra;
import io.smallrye.mutiny.Uni;

public interface SecurityInfra {

    Uni<Void> createUser(UserInfra userInfra);
}
