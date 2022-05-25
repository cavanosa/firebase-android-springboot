package com.tutorial.apirestfirebase.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;


public class TokenModel extends AbstractAuthenticationToken {

    private String token;

    public TokenModel(String token) {
        super(null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
