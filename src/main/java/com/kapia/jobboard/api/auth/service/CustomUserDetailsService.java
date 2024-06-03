package com.kapia.jobboard.api.auth.service;

import com.kapia.jobboard.api.auth.model.AppUser;
import com.kapia.jobboard.api.auth.repository.UserRepository;
import com.kapia.jobboard.api.auth.util.AppUserAdapter;
import com.kapia.jobboard.api.data.constants.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * CustomUserDetailsService is a service class that implements the UserDetailsService interface.
 * This class is responsible for loading user details from the database.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    /**
     * Constructs a new CustomUserDetailsService with the specified UserRepository.
     *
     * @param userRepository the UserRepository to use
     */
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by their username.
     *
     * @param username the username of the user to load
     * @return the UserDetails of the user
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND));

        return new AppUserAdapter(appUser);

    }
}
