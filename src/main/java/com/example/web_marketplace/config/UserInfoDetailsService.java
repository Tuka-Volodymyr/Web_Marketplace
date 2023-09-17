package com.example.web_marketplace.config;

import com.example.web_marketplace.data.UserData;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserInfoDetailsService implements UserDetailsService {
    private final UserData userData;

    public UserInfoDetailsService(UserData userData) {
        this.userData = userData;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserInfoDetails(userData.findByEmail(username));
    }
}
