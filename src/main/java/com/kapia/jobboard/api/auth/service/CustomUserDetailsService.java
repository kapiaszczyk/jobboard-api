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

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND));

        return new AppUserAdapter(appUser);

    }
}
