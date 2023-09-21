package br.dev.marco.config.env;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "secrets", namingStrategy = ConfigMapping.NamingStrategy.VERBATIM)
public interface SecretsConfig {
    @WithName("openAi")
    String openAiSecretName();

    @WithName("keycloak")
    String keycloakSecretName();
}
