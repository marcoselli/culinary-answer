package br.dev.marco.infra.security.sso;

import br.dev.marco.infra.security.sso.request.UserInfra;
import br.dev.marco.infra.security.vault.exceptions.CredentialException;
import io.smallrye.mutiny.Uni;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface SSO {

    Uni<Void> createUser(UserInfra userInfra) throws CredentialException;

    Uni<Void> updateUserGroups(String username, List<String> groups) throws CredentialException;

    Uni<UserRepresentation> getUserRepresentationByUsername(String username);
}
