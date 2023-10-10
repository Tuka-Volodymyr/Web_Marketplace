package com.example.web_marketplace.repositories;

import com.example.web_marketplace.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByIdBuyerAccount(long id);
}
