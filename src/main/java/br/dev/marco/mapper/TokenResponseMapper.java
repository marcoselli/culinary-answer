package br.dev.marco.mapper;

import br.dev.marco.infra.web.response.TokenResponse;
import org.keycloak.representations.AccessTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface TokenResponseMapper {

    @Mapping(target = "accessToken", source = "token")
    TokenResponse from(AccessTokenResponse accessTokenResponse);
}
