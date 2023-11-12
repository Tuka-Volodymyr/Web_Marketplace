package com.example.web_marketplace.config;

import com.example.web_marketplace.repository.impl.UserRepositoryImplement;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserInfoDetailsService implements UserDetailsService {
    private final UserRepositoryImplement userRepositoryImplement;

    public UserInfoDetailsService(UserRepositoryImplement userRepositoryImplement) {
        this.userRepositoryImplement = userRepositoryImplement;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserInfoDetails(userRepositoryImplement.findByEmail(username));
    }
}
