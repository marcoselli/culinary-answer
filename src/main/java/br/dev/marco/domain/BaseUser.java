package br.dev.marco.domain;

import lombok.Data;

@Data
public abstract class BaseUser {
    private String username;
    private String password;
    private String role;

    public BaseUser() {
    }
    public BaseUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
