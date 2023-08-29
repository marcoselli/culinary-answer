package br.dev.marco.infra.security.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfra {
    private String username;
    private String password;
    private String role;
}
