package br.dev.marco.infra.security.sso;

import br.dev.marco.infra.security.sso.request.UserInfra;
import io.smallrye.mutiny.Uni;

public interface SecurityInfra {

    Uni<Void> createUser(UserInfra userInfra);
}
