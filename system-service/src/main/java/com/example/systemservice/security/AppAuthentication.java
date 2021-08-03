package com.example.systemservice.security;

import com.example.systemservice.domain.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AppAuthentication implements Authentication {

    private final User user;
    private boolean authenticated;

    public AppAuthentication(User user) {
        this.user = user;
        this.authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.user;
    }

    @Override
    public Object getPrincipal() {
        return this.user.getIdUser();
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    @Override
    public String getName() {
        return null;
    }
}
