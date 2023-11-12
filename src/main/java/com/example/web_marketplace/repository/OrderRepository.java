package com.example.web_marketplace.repository;

import com.example.web_marketplace.model.entities.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByIdBuyerAccount(long id, Sort sort);
}
