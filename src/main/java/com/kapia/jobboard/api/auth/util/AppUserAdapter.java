package com.kapia.jobboard.api.auth.util;

import com.kapia.jobboard.api.auth.converters.SimpleGrantedAuthorityConverter;
import com.kapia.jobboard.api.auth.model.AppUser;
import com.kapia.jobboard.api.data.constants.Defaults;
import jakarta.persistence.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class AppUserAdapter implements UserDetails {

    @Autowired
    private final AppUser appUser;

    @Convert(converter = SimpleGrantedAuthorityConverter.class)
    private Set<SimpleGrantedAuthority> authorities;

    public AppUserAdapter(AppUser appUser) {
        this.appUser = appUser;
        this.authorities = Set.of(new SimpleGrantedAuthority(Defaults.ROLE_PREFIX + appUser.getRole().getName().getValue()));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

