package com.example.web_marketplace.data;

import com.example.web_marketplace.entities.User;
import com.example.web_marketplace.exceptions.BadRequestException;
import com.example.web_marketplace.exceptions.UserNotFoundException;
import com.example.web_marketplace.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserData implements Data<User>{
    private final UserRepository userRepository;
    public UserData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void save(User user) {
        userRepository.save(user);
    }
    @Override
    public void delete(User user) {
        userRepository.save(user);
    }

    public boolean isExist(String email){
        Optional<User> sellerOptional= userRepository.findByEmailIgnoreCase(email);
        return sellerOptional.isPresent();
    }

    public User findByEmail(String email){
        if(!isExist(email))throw new BadRequestException(
                "Email don`t exist"
        );
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow();
    }
}
