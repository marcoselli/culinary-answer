package br.dev.marco.infra.web;

import br.dev.marco.infra.web.request.CreateUserRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Path("/api/admin")
public class SecurityControllerImpl {

    private final Logger LOGGER = LoggerFactory.getLogger(SecurityControllerImpl.class);

    @POST
    @Path("/user/{userType}")
//    @RolesAllowed("admin")
    public Uni<Response> add(@NotNull @Valid CreateUserRequest createUserRequest){
        return null;
    }
}
