package br.dev.marco.infra.security.impl;

import br.dev.marco.infra.security.SecurityInfra;
import br.dev.marco.infra.security.exceptions.SecurityException;
import br.dev.marco.infra.security.exceptions.model.KeycloakErrorMessage;
import br.dev.marco.infra.security.request.UserInfra;
import br.dev.marco.mapper.UserRepresentationMapper;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
public class KeycloakAdmin implements SecurityInfra {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakAdmin.class);

    private final String REALM_NAME = "culinary-answer";

    @Inject
    Keycloak keycloak;

    @Inject
    UserRepresentationMapper userRepresentationMapper;

    private RealmResource keycloakInstance() {
        return keycloak.realm(REALM_NAME);
    }

    @Override
    public Uni<Void> createUser(UserInfra userInfra) {
        LOGGER.info(" Username: {} - Starting Keycloak user creation.", userInfra.getUsername());
        return Uni.createFrom().item(keycloakInstance())
                .map(realm -> realm.users().create(userRepresentationMapper.from(userInfra)))
                .flatMap(keycloakResponse -> {
                    if(keycloakResponse.getStatus() == HttpResponseStatus.CREATED.code()){
                        LOGGER.info("Username: {} - Keycloak user created successfully. ", userInfra.getUsername());
                        return Uni.createFrom().voidItem();
                    }
                    LOGGER.info(" Username: {} - Failed to create keycloak user.", userInfra.getUsername());
                    return Uni.createFrom().failure(new SecurityException(keycloakResponse.readEntity(KeycloakErrorMessage.class)));
                })
                .onFailure()
                .invoke(err -> LOGGER.error("An unexpected error occurred while creating the user: {}. Fail with: {}", userInfra.getUsername(), err));
    }

}
