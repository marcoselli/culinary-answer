package br.dev.marco.infra.security.vault.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OpenAISecret {
    private String apiToken;
    private Integer maxTokens;
    private String modelType;
}
