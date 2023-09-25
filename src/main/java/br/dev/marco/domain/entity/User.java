package br.dev.marco.domain.entity;

import br.dev.marco.domain.exceptions.PasswordException;
import br.dev.marco.domain.exceptions.UsernameException;
import br.dev.marco.domain.usecase.enuns.UserType;
import br.dev.marco.domain.usecase.exceptions.UserCreationException;

public class User {
    private Username username;
    private Password password;
    private UserType userType;
    public User(String username, String password, String userType) throws PasswordException, UsernameException, UserCreationException {
        this.username = new Username(username);
        this.password = new Password(password);
        assignRole(userType);
    }

    public void changePassword(String password) throws PasswordException {
        this.password = new Password(password);
    }

    private void assignRole(String userType) throws UserCreationException {
        if(userType.equals(UserType.FREE.getType())) this.userType = UserType.FREE;
        else if (userType.equals(UserType.MEMBER.getType())) this.userType = UserType.MEMBER;
        else throw new UserCreationException("Invalid user type");
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    //    public void assignFreeUser() {
//        this.role = UserType.FREE.getRoleName();
//    }
//
//    public void assignMemberUser() {
//        this.role = UserType.MEMBER.getRoleName();
//    }
//
//    public Boolean isMember() {
//        return this.role.equals(UserType.MEMBER.getRoleName());
//    }
}
