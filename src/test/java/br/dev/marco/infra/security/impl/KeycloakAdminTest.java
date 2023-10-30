package br.dev.marco.infra.security.impl;

import br.dev.marco.infra.security.sso.exceptions.SecurityException;
import br.dev.marco.infra.security.sso.exceptions.model.KeycloakErrorMessage;
import br.dev.marco.infra.security.sso.request.UserInfra;
import br.dev.marco.infra.security.sso.impl.KeycloakAdmin;
import br.dev.marco.infra.security.vault.exceptions.CredentialException;
import br.dev.marco.mapper.UserRepresentationMapper;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class KeycloakAdminTest {
    @Mock
    Keycloak keycloak;
    @Mock
    UserRepresentationMapper userRepresentationMapper;

    @Mock
    RealmResource realmResource;

    @Mock
    UsersResource usersResource;

    @InjectMocks
    KeycloakAdmin keycloakAdmin;


    UserInfra userInfra;


    @BeforeEach
    void setUp() {
        when(keycloak.realm(anyString())).thenReturn(realmResource);
        MockitoAnnotations.openMocks(this);
        userInfra = UserInfra.builder()
                .username("testUser")
                .password("Test@123")
                .group("simple-user")
                .build();
    }

    //@Test
    @DisplayName("Create a Keycloak User correctly")
    void testCreateUser() throws CredentialException {
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(Response.status(201).build());

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername("testUser");
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue("Test@123");
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        userRepresentation.setGroups(List.of("simple-user"));
        userRepresentation.setEnabled(true);

        when(userRepresentationMapper.from(any(UserInfra.class))).thenReturn(userRepresentation);

        keycloakAdmin.createUser(userInfra)
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertCompleted();
    }

    //@Test
    @DisplayName("Should throw an exception when create user")
    void shouldThrowErrorWhenCreateUser() throws CredentialException {
//        when(realmResource.users()).thenReturn(usersResource);
//        var keycloakErrorMessage = new KeycloakErrorMessage("This is simulation of error");
//        var response = Response.status(500).entity(keycloakErrorMessage).build();
//        when(usersResource.create(any(UserRepresentation.class))).thenReturn(response);
//        when(userRepresentationMapper.from(any(UserInfra.class))).thenReturn(new UserRepresentation());
//        keycloakAdmin.createUser(userInfra)
//                .subscribe()
//                .withSubscriber(UniAssertSubscriber.create())
//                .assertFailedWith(new SecurityException(keycloakErrorMessage).getClass());

    }

    //@Test
    @DisplayName("Update user groups")
    void updateUserGroup() {
//        realmResource.users().search()
    }

}

