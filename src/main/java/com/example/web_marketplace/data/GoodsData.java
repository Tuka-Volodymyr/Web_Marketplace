package com.example.web_marketplace.data;

import com.example.web_marketplace.dto.FilterForm;
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
    public List<Goods> findByMinPrice(long price){
        return goodsRepository.findByPriceGreaterThanEqual(price);
    }
//    public List<Goods> findByMaxPrice(long price){
//        return goodsRepository.findByPriceLessThanEqual(price);
//    }
    public List<Goods> findByMinPriceAndMax(FilterForm filterForm){
        return goodsRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(filterForm.getMinPrice(),filterForm.getMaxPrice());
    }
    public List<Goods> findByMinPriceAndCategory(FilterForm filter){
        return goodsRepository
                .findByCategoryAndPriceGreaterThanEqual(filter.getCategory(),filter.getMinPrice());
    }
//    public List<Goods> findByMaxPriceAndCategory(FilterForm filter){
//        return goodsRepository
//                .findByCategoryAndPriceLessThanEqual(filter.getCategory(),filter.getMinPrice());
//    }

    public List<Goods> findByMinPriceAndCategoryAndMaxPrice(FilterForm filter){
        return goodsRepository
                .findByCategoryAndPriceGreaterThanEqualAndPriceLessThanEqual(filter.getCategory(),filter.getMinPrice(),filter.getMaxPrice());
    }
//    public List<Goods> findByCategory(String category){
//        return goodsRepository.findByCategory(category);
//    }
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
