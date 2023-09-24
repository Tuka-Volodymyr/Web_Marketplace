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
    List<Goods> findByPriceGreaterThanEqual(long minPrice);
    List<Goods> findByPriceLessThanEqual(long maxPrice);
    List<Goods> findByPriceGreaterThanEqualAndPriceLessThanEqual(long minPrice,long maxPrice);
    List<Goods> findByCategoryAndPriceLessThanEqual(String category,long maxPrice);
    List<Goods> findByUserEmail(String category);



    List<Goods> findByCategoryAndPriceGreaterThanEqual(String category,long price);
    List<Goods> findByCategoryAndPriceGreaterThanEqualAndPriceLessThanEqual(String category,long minPrice,long maxPrice);
}
