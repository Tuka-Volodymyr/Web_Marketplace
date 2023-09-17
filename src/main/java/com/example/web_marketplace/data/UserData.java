package com.example.web_marketplace.data;

import com.example.web_marketplace.entities.User;
import com.example.web_marketplace.exceptions.UserNotFoundException;
import com.example.web_marketplace.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserData {
    private final UserRepository userRepository;

    public UserData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void save(User user) {
        userRepository.save(user);
    }
    public void delete(User user) {
        userRepository.save(user);
    }
    public boolean isExist(String email){
        Optional<User> sellerOptional= userRepository.findByEmailIgnoreCase(email);
        return sellerOptional.isPresent();
    }
    public User findByEmail(String email){
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(UserNotFoundException::new);
    }
}
