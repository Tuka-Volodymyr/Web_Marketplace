package com.example.web_marketplace.repository.impl;

import com.example.web_marketplace.model.entities.Order;
import com.example.web_marketplace.repository.OrderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderRepositoryImplement {
    private final OrderRepository orderRepository;

    public OrderRepositoryImplement(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }



    public void delete(Order data) {

    }
    public List<Order> findByUserId(long idAccount, Sort sort){
        return orderRepository.findByIdBuyerAccount(idAccount,sort);
    }


    public void save(Order data) {
        orderRepository.save(data);
    }
}
