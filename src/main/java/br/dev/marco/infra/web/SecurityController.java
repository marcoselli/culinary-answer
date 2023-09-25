package br.dev.marco.infra.web;

import br.dev.marco.domain.entity.User;
import br.dev.marco.domain.exceptions.PasswordException;
import br.dev.marco.domain.exceptions.UsernameException;
import br.dev.marco.domain.usecase.impl.CreateUser;
import br.dev.marco.infra.security.vault.exceptions.CredentialException;
import br.dev.marco.infra.web.request.CreateUserRequest;
import br.dev.marco.infra.web.request.LoginRequest;
import br.dev.marco.infra.web.response.TokenResponse;
import br.dev.marco.mapper.UserAdapter;
import br.dev.marco.domain.usecase.Command;
import br.dev.marco.domain.usecase.exceptions.UserCreationException;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NoSuchObjectException;


@ApplicationScoped
@Path("/v1/sso")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Blocking
public class SecurityController{

    private final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);
    private final CreateUser createUser;

    private final Command<LoginRequest,TokenResponse> generateToken;
    private final UserAdapter userAdapter;

    @Inject
    public SecurityController(@Named("createUser") CreateUser createUser,
                              @Named("generateToken") Command<LoginRequest, TokenResponse> generateToken,
                              UserAdapter userAdapter) {
        this.createUser = createUser;
        this.generateToken = generateToken;
        this.userAdapter = userAdapter;
    }

    @GET
    @Path("/token")
    @PermitAll
    @Operation(description = "Return user token after successful login")
    @APIResponse(
            responseCode = "200",
            description = "Token returned successfully"
    )
    @APIResponse(responseCode = "400", description = "Failed to login")
    @APIResponse(responseCode = "500", description = "Some unexpected error occurred")
    public Uni<Response> retrieveToken(LoginRequest loginRequest) throws CredentialException, NoSuchObjectException, NoSuchFieldException {
        return generateToken.execute(loginRequest)
                .map(response -> Response.ok(response).build());
    }
    
    @POST
    @Path("/user/{userType}")
    @RolesAllowed("admin")
    @Operation(description = "Create a user")
    @APIResponse(
            responseCode = "201",
            description = "User created successfully"
    )
    @APIResponse(responseCode = "400", description = "Bad request")
    @APIResponse(responseCode = "500", description = "Some unexpected error occurred")
    public Uni<Response> add(@NotNull @Valid CreateUserRequest createUserRequest, String userType)
            throws PasswordException, UsernameException, UserCreationException, NoSuchObjectException, NoSuchFieldException, CredentialException {
        return createUser.execute(userAdapter.from(createUserRequest, userType))
                .map(response -> Response.status(HttpResponseStatus.CREATED.code()).build());
    }

//    @GET
//    @RolesAllowed("admin")
//    public Uni<Response>  updateUserGroups() {
////        LOGGER.info("Username: {} - Updating Keycloak user groups");
//        return Uni.createFrom().item(keycloak.realm("culinary-answer"))
//                .map(realm -> realm.users().search("teste").stream().findFirst().orElseThrow(() -> new NoSuchElementException("")))
//                .map(user -> {
//                    user.setGroups(List.of("simple-user"));
//                    return user;
//                })
//                .map(res -> Response.status(200).entity(res).build());
//    }

}
