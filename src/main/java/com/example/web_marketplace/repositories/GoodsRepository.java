package com.example.web_marketplace.repositories;

import com.example.web_marketplace.entities.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<Goods,Long> {
    Optional<Goods> findByNameAndUserEmail(String name, String email);
    List<Goods> findByCategory(String category);
    List<Goods> findByUserEmail(String category);
}
