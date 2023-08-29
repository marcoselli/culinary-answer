package br.dev.marco.domain;

import br.dev.marco.domain.exception.PasswordException;
import br.dev.marco.domain.exception.UsernameException;
import br.dev.marco.usecase.enuns.UserType;
import lombok.Data;

public class User {
    private Username username;
    private Password password;
    private String role;
    public User(String username, String password) throws PasswordException, UsernameException {
        this.username = new Username(username);
        this.password = new Password(password);
    }

    public Username getUsername() {
        return username;
    }

    public void changePassword(String password) throws PasswordException {
        this.password = new Password(password);
    }

    public void assignFreeUser() {
        this.role = UserType.FREE.getRoleName();
    }

    public void assignMemberUser() {
        this.role = UserType.MEMBER.getRoleName();
    }
}
