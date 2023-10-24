package com.example.web_marketplace.data;

import com.example.web_marketplace.entities.Basket;
import com.example.web_marketplace.entities.Goods;
import com.example.web_marketplace.exceptions.BadRequestException;
import com.example.web_marketplace.repositories.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BasketData implements Data<Basket>{
    private final BasketRepository basketRepository;

    public BasketData(BasketRepository basketRepository) {
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
        if(basket.isEmpty())throw new BadRequestException("Goods don`t exist in basket!");
        return basket;
    }
    public List<Basket> findByUser(long idUser){
        return basketRepository.findByIdUser(idUser);
    }

    @Override
    public void delete(Basket data) {
        basketRepository.delete(data);
    }

    @Override
    public void save(Basket data) {
        basketRepository.save(data);
    }
}
