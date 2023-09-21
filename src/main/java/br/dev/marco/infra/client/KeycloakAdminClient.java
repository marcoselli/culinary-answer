package br.dev.marco.infra.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.keycloak.representations.AccessTokenResponse;
import retrofit2.http.FormUrlEncoded;


@RegisterRestClient(configKey = "keycloakAdmin")
public interface KeycloakAdminClient {

    @POST
    @Path("/realms/{realmName}/protocol/openid-connect/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<AccessTokenResponse> oidcToken(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
                                       @PathParam("realmName") String realmName,
                                       Form loginForm);
}
