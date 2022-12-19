package com.example.kunuz.config.security;


import com.example.kunuz.config.security.CustomUserDetail;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.repository.ProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository repository;

    public CustomUserDetailsService(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<ProfileEntity> profile = repository.findByEmail(login);

        if (profile.isEmpty()) {
            throw new UsernameNotFoundException("Bad Cretensional");
        }

        return new CustomUserDetail(profile.get());

    }
}
