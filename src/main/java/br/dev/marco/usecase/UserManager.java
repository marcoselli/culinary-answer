package br.dev.marco.usecase;

import br.dev.marco.domain.FreeUser;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class UserManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManager.class);

    private final String CULINARY_REALM = "culinary-answer";

    @Inject
    Keycloak keycloak;

    public Uni<Response> createFreeUser(FreeUser freeUser) {
        LOGGER.info("Starting free user creation. Username: {}", freeUser.getUsername());
        return Uni.createFrom().item(freeUser)
                .map(user -> {
                    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                    credentialRepresentation.setValue(freeUser.getPassword());

                    UserRepresentation userRepresentation = new UserRepresentation();
                    userRepresentation.setUsername(freeUser.getUsername());
                    userRepresentation.setCredentials(List.of(credentialRepresentation));
                    userRepresentation.setEnabled(Boolean.TRUE);

                    return userRepresentation;
                })
                .map(userRepresentation -> keycloak.realm(CULINARY_REALM)
                        .users()
                        .create(userRepresentation)
                )
                .invoke(keycloakResponse -> {
                    if(keycloakResponse.getStatus() == HttpResponseStatus.CREATED.code())
                        LOGGER.info("User created successfully. Username: {}", freeUser.getUsername());
                    else
                        LOGGER.info("Failed to create user: {}", freeUser.getUsername());
                })
                .onFailure()
                .invoke(err -> LOGGER.error("An unexpected error occurred while creating the user: {}. Fail with: {}", freeUser.getUsername(), err));
    }

}
