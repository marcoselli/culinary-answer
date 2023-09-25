package br.dev.marco.infra.security.sso.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfra {
    private String username;
    private String password;
    private String group;
}
