package br.dev.marco.domain.entity;

import br.dev.marco.domain.exceptions.PasswordException;

import java.util.regex.Pattern;

public class Password {

    private final String PASSWORD_RULE_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])(?=.{8,16}$).*";
    private String value;

    public Password(String password) throws PasswordException {
        validate(password);
        this.value = password;
    }

    private void validate(String password) throws PasswordException {
        //Should have at least:
        //08 characters, 1 number, 1 capital letter, 1 special character
        //Maximum length: 16 characters
        var pattern = Pattern.compile(PASSWORD_RULE_REGEX);
        var matcher = pattern.matcher(password);
        if(!matcher.matches()) throw new PasswordException("Password does not meet security requirements");
    }

    public String getValue() {
        return value;
    }
}
