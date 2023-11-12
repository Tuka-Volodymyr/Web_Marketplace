package com.example.web_marketplace.repository.impl;

import com.example.web_marketplace.model.entities.Rating;
import com.example.web_marketplace.repository.RatingRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RatingRepositoryImplement {
    private final RatingRepository repository;

    public RatingRepositoryImplement(RatingRepository repository) {
        this.repository = repository;
    }


    public void delete(Rating data) {

    }


    public void save(Rating data) {
        repository.save(data);
    }

    public Optional<Rating> findBySeller(long id){
        return repository.findByIdSeller(id);
    }
}
