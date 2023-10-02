package com.example.web_marketplace.data;

import com.example.web_marketplace.exceptions.BadRequestException;
import com.example.web_marketplace.forms.FilterForm;
import com.example.web_marketplace.entities.Goods;
import com.example.web_marketplace.exceptions.GoodsNotFoundException;
import com.example.web_marketplace.repositories.GoodsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GoodsData implements Data<Goods>{
    private final GoodsRepository goodsRepository;

    public GoodsData(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }
    @Override
    public void save(Goods goods) {
        goodsRepository.save(goods);
    }
    @Override
    public void delete(Goods goods) {
        goodsRepository.delete(goods);
    }

    public Goods findById(long id){
        Optional<Goods> goods= goodsRepository.findById(id);
        if(goods.isEmpty())throw new BadRequestException("Goods don`t exist!");
        return goods.get();
    }
    public List<Goods> findByMinPrice(long price, Sort sort){
        return goodsRepository.findByPriceGreaterThanEqual(price, sort);
    }

    public List<Goods> findByMinPriceAndMax(FilterForm filterForm, Sort sort){
        return goodsRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(filterForm.getMinPrice(),filterForm.getMaxPrice(), sort);
    }
    public List<Goods> findByMinPriceAndCategory(FilterForm filter, Sort sort){
        return goodsRepository
                .findByCategoryAndPriceGreaterThanEqual(filter.getCategory(),filter.getMinPrice(), sort);
    }


    public List<Goods> findByMinPriceAndCategoryAndMaxPrice(FilterForm filter, Sort sort){
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
