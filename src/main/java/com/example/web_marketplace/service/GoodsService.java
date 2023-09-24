package com.example.web_marketplace.service;

import com.example.web_marketplace.data.GoodsData;
import com.example.web_marketplace.data.UserData;
import com.example.web_marketplace.dto.FilterForm;
import com.example.web_marketplace.entities.Goods;
import com.example.web_marketplace.entities.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import java.time.LocalDate;

@Service
public class GoodsService {
    private final GoodsData goodsData;
    private final UserData userData;
    public GoodsService(GoodsData goodsData, UserData userData) {
        this.userData = userData;
        this.goodsData = goodsData;
    }
    public void addGoods(Goods goods){
        UserDetails userDetails=getUserDetails();
        goods.setLocalDate(LocalDate.now());
        goods.setUserEmail(userDetails.getUsername());
        goodsData.save(goods);
    }
    public String deleteGoods(String name){
        UserDetails userDetails=getUserDetails();
        User user = userData.findByEmail(userDetails.getUsername());
        goodsData.delete(goodsData.findByNameAndSellerID(name,userDetails.getUsername()));
        return name+" was deleted!";
    }
    public void getGoods(Model model){
       model.addAttribute("goods",goodsData.findAll());
    }
    public void getYourGoods(Model model){
        UserDetails userDetails=getUserDetails();
        model.addAttribute("goods",goodsData.findByEmail(userDetails.getUsername()));
    }
    public void getFoundGoodsWithFilter(Model model, FilterForm filter){
        if (!filter.getCategory().isBlank() && filter.getMaxPrice()> filter.getMinPrice()) {
            model.addAttribute("goods",goodsData.findByMinPriceAndCategoryAndMaxPrice(filter));
        }else if (!filter.getCategory().isBlank() ) {
            model.addAttribute("goods", goodsData.findByMinPriceAndCategory(filter));
        } else if (filter.getMaxPrice() > filter.getMinPrice()) {
            model.addAttribute("goods",goodsData.findByMinPriceAndMax(filter));
        }else if (filter.getMinPrice() > 0){
            model.addAttribute("goods",goodsData.findByMinPrice(filter.getMinPrice()));

        }
    }


    private UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
