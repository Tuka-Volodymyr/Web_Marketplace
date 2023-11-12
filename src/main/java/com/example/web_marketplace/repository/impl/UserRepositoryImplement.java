package com.example.web_marketplace.repository.impl;

import com.example.web_marketplace.model.entities.User;
import com.example.web_marketplace.exceptions.BadRequestException;
import com.example.web_marketplace.exceptions.UserNotFoundException;
import com.example.web_marketplace.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImplement {
    private final UserRepository userRepository;
    public UserRepositoryImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.save(user);
    }
    public User findById(long id){

        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);

    }
    public boolean isExist(String email){
        Optional<User> sellerOptional= userRepository.findByEmailIgnoreCase(email);
        return sellerOptional.isPresent();
    }

    public User findByEmail(String email){
        if(!isExist(email))throw new BadRequestException(
                "Email don`t exist "+email
        );
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow();
    }
}
