package com.example.web_marketplace.repository.impl;

import com.example.web_marketplace.model.entities.Basket;
import com.example.web_marketplace.repository.BasketRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BasketRepositoryImplement {
    private final BasketRepository basketRepository;

    public BasketRepositoryImplement(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }


    public void deleteList(List<Basket> data) {
        basketRepository.deleteAll(data);

    }

    public List<Basket> findByGoodsId(long idGoods){
       return basketRepository.findByIdGoods(idGoods);
    }
    public List<Basket> findByIds(Long idUser,Long idGood){
        List<Basket> basket= basketRepository.findByIdUserAndIdGoods(idUser,idGood);
//        if(basket.isEmpty())throw new BadRequestException("Goods don`t exist in basket!");
        return basket;
    }
    public List<Basket> findByUser(long idUser){
        return basketRepository.findByIdUser(idUser);
    }


    public void delete(Basket data) {
        basketRepository.delete(data);
    }


    public void save(Basket data) {
        basketRepository.save(data);
    }
}
