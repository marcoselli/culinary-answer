package br.dev.marco.infra.web;

import br.dev.marco.domain.entity.User;
import br.dev.marco.domain.exception.PasswordException;
import br.dev.marco.domain.exception.UsernameException;
import br.dev.marco.infra.web.request.CreateUserRequest;
import br.dev.marco.mapper.UserAdapter;
import br.dev.marco.usecase.Command;
import br.dev.marco.usecase.exceptions.UserCreationException;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NoSuchObjectException;

@ApplicationScoped
@Path("/api/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SecurityController {

    private final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);
    private final Command<User,Void> createUser;
    private final UserAdapter userAdapter;

    @Inject
    public SecurityController(@Named("createUser") Command<User, Void> createUser, UserAdapter userAdapter) {
        this.createUser = createUser;
        this.userAdapter = userAdapter;
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
    @Blocking
    public Uni<Response> add(@NotNull @Valid CreateUserRequest createUserRequest, String userType)
            throws PasswordException, UsernameException, UserCreationException, NoSuchObjectException, NoSuchFieldException {
        return createUser.execute(userAdapter.from(createUserRequest, userType))
                .map(response -> Response.status(HttpResponseStatus.CREATED.code()).build());
    }
}
