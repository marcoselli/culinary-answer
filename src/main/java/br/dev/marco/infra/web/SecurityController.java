package br.dev.marco.infra.web;

import br.dev.marco.infra.web.request.CreateUserRequest;
import br.dev.marco.infra.web.response.QuestionResponse;
import br.dev.marco.mapper.FreeUserMapper;
import br.dev.marco.usecase.UserManager;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Path("/api/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SecurityController {

    private final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

    @Inject
    UserManager userManager;

    @POST
    @Path("/user/{userType}")
    @RolesAllowed("admin")
    @Operation(description = "Create a free user")
    @APIResponse(
            responseCode = "201",
            description = "User created successfully",
            content = @Content(schema = @Schema(implementation = QuestionResponse.class))
    )
    @APIResponse(responseCode = "400", description = "Bad request")
    @APIResponse(responseCode = "500", description = "Some unexpected error occurred")
    @Blocking
    public Uni<Response> add(CreateUserRequest createUserRequest, HttpHeaders headers){
        return userManager.createFreeUser(FreeUserMapper.from(createUserRequest));
    }
}