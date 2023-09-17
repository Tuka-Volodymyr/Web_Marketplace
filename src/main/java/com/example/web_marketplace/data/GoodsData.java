package com.example.web_marketplace.data;

import com.example.web_marketplace.entities.Goods;
import com.example.web_marketplace.exceptions.GoodsNotFoundException;
import com.example.web_marketplace.repositories.GoodsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GoodsData implements Data{
    private final GoodsRepository goodsRepository;

    public GoodsData(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    public Goods findByNameAndSellerID(String name, String email){
        return goodsRepository
                .findByNameAndUserEmail(name,email)
                .orElseThrow(GoodsNotFoundException::new);
    }
    public List<Goods> findByCategory(String category){
        return goodsRepository.findByCategory(category);
    }
    public List<Goods> findByEmail(String email){
        return goodsRepository.findByUserEmail(email);
    }
    public List<Goods> findAll(){
        return goodsRepository.findAll();
    }
    @Override
    public void save(Goods goods) {
        goodsRepository.save(goods);
    }
    public void delete(Goods goods) {
        goodsRepository.delete(goods);
    }
    public boolean isExist(String name,String email){
        Optional<Goods> commodityOptional= goodsRepository.findByNameAndUserEmail(name,email);
        return commodityOptional.isPresent();
    }

}
