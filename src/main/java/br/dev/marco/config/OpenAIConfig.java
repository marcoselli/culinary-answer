package br.dev.marco.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "secret", namingStrategy = ConfigMapping.NamingStrategy.VERBATIM)
public interface OpenAIConfig {
    @WithName("API_TOKEN")
    String apiToken();
}
