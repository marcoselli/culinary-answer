package br.dev.marco.mapper;

import br.dev.marco.infra.security.request.UserInfra;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface UserRepresentationMapper {

    @Mapping(target = "credentials", source = "userInfra", qualifiedByName = "credentialsMapper")
    @Mapping(target = "groups", source = "userInfra",qualifiedByName = "realmRolesMapper")
    @Mapping(target = "enabled", source = "userInfra", constant = "true")
    UserRepresentation from(UserInfra userInfra);

    @Named(value = "credentialsMapper")
    default List<CredentialRepresentation> toCredentialRepresentation(UserInfra userInfra) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(userInfra.getPassword());
        return List.of(credentialRepresentation);
    }

    @Named(value = "realmRolesMapper")
    default List<String> toGroups(UserInfra userInfra) {
        return List.of(userInfra.getRole());
    }

}
