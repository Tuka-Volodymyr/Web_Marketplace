package com.example.web_marketplace.service;

import com.example.web_marketplace.data.GoodsData;
import com.example.web_marketplace.data.UserData;
import com.example.web_marketplace.entities.Goods;
import com.example.web_marketplace.entities.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import java.time.LocalDate;
import java.util.List;

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
    public List<Goods> getGoods(String category){
        if(category==null)return goodsData.findAll();
        return goodsData.findByCategory(category);
    }
    public Model getYourGoods(Model model){
        UserDetails userDetails=getUserDetails();
        return model.addAttribute("goods",goodsData.findByEmail(userDetails.getUsername()));
    }

    private UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
