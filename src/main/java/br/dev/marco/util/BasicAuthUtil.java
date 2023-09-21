package br.dev.marco.util;


import jakarta.enterprise.context.ApplicationScoped;

import java.util.Base64;

@ApplicationScoped
public class BasicAuthUtil {
    public String generateHeader(String username, String password) {
        var sb = new StringBuilder();
        sb.append("Basic");
        sb.append(Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
        return "Basic " + Base64.getEncoder().encodeToString("backend-service:secret".getBytes());
    }
}
