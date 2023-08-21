package br.dev.marco.infra.web.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotNull @NotEmpty
        String username,
        @NotNull @NotEmpty
        String password
) {}
