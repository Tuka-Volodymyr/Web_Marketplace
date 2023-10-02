package com.example.web_marketplace.repositories;

import com.example.web_marketplace.entities.TotalPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TotalPriceRepository extends JpaRepository<TotalPrice,Long> {
    Optional<TotalPrice> findTotalPriceByIdUser(long idUser);
}
