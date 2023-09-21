package br.dev.marco.infra.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(String accessToken,
                            String expiresIn,
                            String refreshExpiresIn,
                            String refreshToken,
                            String tokenType,
                            String sessionState) {
}
