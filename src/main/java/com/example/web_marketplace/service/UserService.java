package com.example.web_marketplace.service;

import com.example.web_marketplace.data.UserData;
import com.example.web_marketplace.entities.User;
import com.example.web_marketplace.exceptions.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
public class UserService {
    private final UserData userData;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserData userData, PasswordEncoder passwordEncoder) {
        this.passwordEncoder=passwordEncoder;
        this.userData = userData;
    }
    public void addUser(User user){
        if(userData.isExist(user.getEmail()))throw new BadRequestException(
                "Email has already used!"
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userData.save(user);

    }
    public void infoOfUser(String email, Model model){
        model.addAttribute("user", userData.findByEmail(email));

    }
}
