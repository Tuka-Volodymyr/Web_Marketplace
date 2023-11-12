package com.example.web_marketplace.repository;

import com.example.web_marketplace.model.entities.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {
    List<Basket> findByIdUser(Long idUser);

    List<Basket> findByIdGoods(long id);


    List<Basket> findByIdUserAndIdGoods(Long idUser, Long idGood);
}
