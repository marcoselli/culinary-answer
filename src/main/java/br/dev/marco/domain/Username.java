package br.dev.marco.domain;

import br.dev.marco.domain.exception.UsernameException;

import java.util.regex.Pattern;

public class Username {

    private final String USERNAME_RULE_REGEX = "^[a-zA-Z0-9]{4,12}$";
    private String value;

    public Username(String value) throws UsernameException {
        validate(value);
        this.value = value;
    }

    private void validate(String username) throws UsernameException {
        //Should have at least 4 characters and maximum 12 characters
        //Not allowed to have special characters or words with accent
        var pattern = Pattern.compile(USERNAME_RULE_REGEX);
        var matcher = pattern.matcher(username);
        if(!matcher.matches()) throw new UsernameException("Username does not meet requirements requested");
    }

    public String getValue() {
        return value;
    }
}
