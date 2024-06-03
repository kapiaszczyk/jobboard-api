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

/**
 * This class adapts the application user to the Spring Security user details.
 */
public class AppUserAdapter implements UserDetails {

    @Autowired
    private final AppUser appUser;

    @Convert(converter = SimpleGrantedAuthorityConverter.class)
    private Set<SimpleGrantedAuthority> authorities;

    /**
     * Constructor.
     *
     * @param appUser The application user.
     */
    public AppUserAdapter(AppUser appUser) {
        this.appUser = appUser;
        this.authorities = Set.of(new SimpleGrantedAuthority(Defaults.ROLE_PREFIX + appUser.getRole().getName().getValue()));
    }

    /**
     * Gets the authorities for the application user.
     *
     * @return The authorities for the application user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Gets the password for the application user.
     *
     * @return The password for the application user.
     */
    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    /**
     * Gets the username for the application user.
     *
     * @return The username for the application user.
     */
    @Override
    public String getUsername() {
        return appUser.getUsername();
    }

    /**
     * Checks if the account is not expired.
     *
     * @return True if the account is not expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks if the account is not locked.
     *
     * @return True if the account is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Checks if the credentials are not expired.
     *
     * @return True if the credentials are not expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Checks if the account is enabled.
     *
     * @return True if the account is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

