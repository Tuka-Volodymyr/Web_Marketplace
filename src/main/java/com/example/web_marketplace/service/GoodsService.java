package com.example.web_marketplace.service;

import com.example.web_marketplace.data.GoodsData;
import com.example.web_marketplace.data.UserData;
import com.example.web_marketplace.forms.FilterForm;
import com.example.web_marketplace.entities.Goods;
import com.example.web_marketplace.entities.User;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsData goodsData;
    private final UserData userData;
    public static final Sort sort = Sort.by(Sort.Direction.ASC, "price");

    public void addGoods(Goods goods){
        UserDetails userDetails=getUserDetails();
        goods.setLocalDate(LocalDate.now());
        goods.setUserEmail(userDetails.getUsername());
        goodsData.save(goods);
    }
    public void deleteGoods(long idGoods){
        UserDetails userDetails=getUserDetails();
        User user = userData.findByEmail(userDetails.getUsername());
        goodsData.delete(goodsData.findById(idGoods));
    }
    public void getGoods(Model model){
       model.addAttribute("goods",goodsData.findAll(sort));
    }
    public void getYourGoods(Model model){
        UserDetails userDetails=getUserDetails();
        model.addAttribute("goods",goodsData.findByEmail(userDetails.getUsername(),sort));
    }
    public void getFoundGoodsWithFilter(Model model, FilterForm filter){

        if (!filter.getCategory().isBlank() && filter.getMaxPrice()> filter.getMinPrice()) {
            model.addAttribute("goods",goodsData.findByMinPriceAndCategoryAndMaxPrice(filter,sort));
        }else if (!filter.getCategory().isBlank() ) {
            model.addAttribute("goods", goodsData.findByMinPriceAndCategory(filter,sort));
        } else if (filter.getMaxPrice() > filter.getMinPrice()) {
            model.addAttribute("goods",goodsData.findByMinPriceAndMax(filter,sort));
        }else if (filter.getMinPrice() > 0){
            model.addAttribute("goods",goodsData.findByMinPrice(filter.getMinPrice(),sort));

        }
    }
    public void getSortDecreaseByPrice(Model model){
//        model.addAttribute("goods",)
    }
    public void getSortIncreaseByPrice(Model model){

    }
    public static UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
