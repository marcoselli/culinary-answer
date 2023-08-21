package br.dev.marco.domain;

import lombok.Data;

public class FreeUser extends BaseUser {
    public FreeUser(String username, String password) {
        super(username,password);
        super.setRole("user");
    }
}
