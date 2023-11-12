package com.example.web_marketplace.repository;

import com.example.web_marketplace.model.entities.Goods;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<Goods,Long> {


//    List<Goods> findByCategory(String category);
    List<Goods> findByPriceGreaterThanEqual(long minPrice, Sort sort);
//    List<Goods> findByPriceLessThanEqual(long maxPrice);
    List<Goods> findByPriceGreaterThanEqualAndPriceLessThanEqual(long minPrice,long maxPrice, Sort sort);
//    List<Goods> findByCategoryAndPriceLessThanEqual(String category,long maxPrice);
    List<Goods> findByUserEmail(String category, Sort sort);

    Optional<Goods> findTopByOrderByIdGoodDesc();



    List<Goods> findByCategoryAndPriceGreaterThanEqual(String category,long price, Sort sort);
    List<Goods> findByCategoryAndPriceGreaterThanEqualAndPriceLessThanEqual(String category,long minPrice,long maxPrice, Sort sort);
}
