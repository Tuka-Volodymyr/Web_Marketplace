package com.example.web_marketplace.repository.impl;

import com.example.web_marketplace.exceptions.BadRequestException;
import com.example.web_marketplace.model.dto.FilterDto;
import com.example.web_marketplace.model.entities.Goods;
import com.example.web_marketplace.repository.GoodsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GoodsRepositoryImplement {
    private final GoodsRepository goodsRepository;

    public GoodsRepositoryImplement(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    public Object save(Goods goods) {
        goodsRepository.save(goods);
        return null;
    }
    public void delete(Goods goods) {

        goodsRepository.delete(goods);
    }

    public Goods findById(long id){
        Optional<Goods> goods= goodsRepository.findById(id);
        if(goods.isEmpty())throw new BadRequestException("Good isn`t exist");
        return goods.get();
    }

    public List<Goods> findByMinPrice(long price, Sort sort){
        return goodsRepository.findByPriceGreaterThanEqual(price, sort);
    }

    public List<Goods> findByMinPriceAndMax(FilterDto filterDto, Sort sort){
        return goodsRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(filterDto.getMinPrice(), filterDto.getMaxPrice(), sort);
    }
    public List<Goods> findByMinPriceAndCategory(FilterDto filter, Sort sort){
        return goodsRepository
                .findByCategoryAndPriceGreaterThanEqual(filter.getCategory(),filter.getMinPrice(), sort);
    }


    public List<Goods> findByMinPriceAndCategoryAndMaxPrice(FilterDto filter, Sort sort){
        return goodsRepository
                .findByCategoryAndPriceGreaterThanEqualAndPriceLessThanEqual(filter.getCategory(),filter.getMinPrice(),filter.getMaxPrice(),sort);
    }



    public List<Goods> findByEmail(String email, Sort sort){
        return goodsRepository.findByUserEmail(email, sort);
    }
    public List<Goods> findAll(Sort sort){
        return goodsRepository.findAll(sort);
    }

    public boolean isExist(Long id,String email){
        Optional<Goods> commodityOptional= goodsRepository.findById(id);
        return commodityOptional.isPresent();
    }
    public Goods findLast(){
        return goodsRepository.findTopByOrderByIdGoodDesc().orElseThrow();
    }
//    public List<Goods> findByMaxPrice(long price){
//        return goodsRepository.findByPriceLessThanEqual(price);
//    }
    //    public List<Goods> findByCategory(String category){
//        return goodsRepository.findByCategory(category);
//    }
    //    public List<Goods> findByMaxPriceAndCategory(FilterForm filter){
//        return goodsRepository
//                .findByCategoryAndPriceLessThanEqual(filter.getCategory(),filter.getMinPrice());
//    }
}
