package com.example.web_marketplace.data;

import com.example.web_marketplace.entities.Order;
import com.example.web_marketplace.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderData implements Data<Order>{
    private final OrderRepository orderRepository;

    public OrderData(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public void delete(Order data) {

    }
    public List<Order> findByUserId(long idAccount, Sort sort){
        return orderRepository.findByIdBuyerAccount(idAccount,sort);
    }

    @Override
    public void save(Order data) {
        orderRepository.save(data);
    }
}
