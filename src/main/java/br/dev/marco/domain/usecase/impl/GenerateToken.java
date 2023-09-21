package br.dev.marco.domain.usecase.impl;

import br.dev.marco.domain.usecase.Command;
import br.dev.marco.infra.client.KeycloakAdminClient;
import br.dev.marco.infra.security.vault.VaultRecoveryManager;
import br.dev.marco.infra.security.vault.exceptions.CredentialException;
import br.dev.marco.infra.security.vault.model.KeycloakSecret;
import br.dev.marco.infra.web.request.LoginRequest;
import br.dev.marco.infra.web.response.TokenResponse;
import br.dev.marco.mapper.TokenFormAdapter;
import br.dev.marco.mapper.TokenResponseMapper;
import br.dev.marco.util.BasicAuthUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
@Named("generateToken")
public class GenerateToken implements Command<LoginRequest,TokenResponse> {

    private final Logger LOGGER = LoggerFactory.getLogger(GenerateToken.class);

    @Inject
    BasicAuthUtil basicAuthUtil;

    @Inject
    @RestClient
    KeycloakAdminClient keycloakAdminClient;

    @Inject
    VaultRecoveryManager<KeycloakSecret> keycloakSecretRecovery;

    @Inject
    TokenFormAdapter tokenFormAdapter;

    @Inject
    TokenResponseMapper tokenResponseMapper;

    @Override
    public Uni<TokenResponse> execute(LoginRequest loginRequest) throws CredentialException {
        var keycloakSecrets = keycloakSecretRecovery.secrets();
        var basicHeader = basicAuthUtil.generateHeader(
                keycloakSecrets.getAdminClientUsername(), keycloakSecrets.getAdminClientPassword());
        return keycloakAdminClient.oidcToken(
                basicHeader, keycloakSecrets.getRealmName(),tokenFormAdapter.from(loginRequest))
                .invoke(() -> LOGGER.info("Username: {} - Login successful", loginRequest.username()))
                .map(token -> tokenResponseMapper.from(token))
                .onFailure()
                .invoke(err ->  LOGGER.error("Username: {} - Login denied. Fail with:", err));
    }
}
