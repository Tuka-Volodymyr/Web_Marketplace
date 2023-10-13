package com.example.web_marketplace.data;

import com.example.web_marketplace.entities.Rating;
import com.example.web_marketplace.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RatingData implements Data<Rating> {
    private final RatingRepository repository;

    public RatingData(RatingRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(Rating data) {

    }

    @Override
    public void save(Rating data) {
        repository.save(data);
    }

    public Optional<Rating> findBySeller(long id){
        return repository.findByIdSeller(id);
    }
}
