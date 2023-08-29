package br.dev.marco.domain;

import br.dev.marco.domain.exception.PasswordException;

import java.util.regex.Pattern;

public class Password {

    private final String PASSWORD_RULE_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])(?=.{8,16}).*$";
    private String value;

    public Password(String value) throws PasswordException {
        validate(value);
        this.value = value;
    }

    public void validate(String password) throws PasswordException {
        //Should have at least:
        //08 characters, 1 number, 1 capital letter, 1 special character
        //Maximum lenght: 16 characters
        var pattern = Pattern.compile(PASSWORD_RULE_REGEX);
        var matcher = pattern.matcher(password);
        if(!matcher.matches()) throw new PasswordException("Password does not meet security requirements");
    }

    public String getValue() {
        return value;
    }
}
