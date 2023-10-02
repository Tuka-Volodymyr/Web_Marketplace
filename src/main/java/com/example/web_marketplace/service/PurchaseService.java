package com.example.web_marketplace.service;

import com.example.web_marketplace.data.BasketData;
import com.example.web_marketplace.data.GoodsData;
import com.example.web_marketplace.data.TotalPriceData;
import com.example.web_marketplace.data.UserData;
import com.example.web_marketplace.entities.Basket;
import com.example.web_marketplace.entities.Goods;
import com.example.web_marketplace.entities.TotalPrice;
import com.example.web_marketplace.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static com.example.web_marketplace.service.GoodsService.getUserDetails;

@Service
public class PurchaseService {
    private final GoodsData goodsData;
    private final UserData userData;
    private final BasketData basketData;
    private final TotalPriceData totalPriceData;

    public PurchaseService(GoodsData goodsData, UserData userData, BasketData basketData, TotalPriceData totalPriceData) {
        this.goodsData = goodsData;
        this.userData=userData;
        this.basketData = basketData;
        this.totalPriceData = totalPriceData;
    }
    public void getBasket(Model model){
        User user=userData.findByEmail(getUserDetails().getUsername());
        List<Basket> basketList=basketData.findByUser(user.getIdUser());
        List<Goods> goodsList=new ArrayList<>();
        for(Basket basket:basketList){
            goodsList.add(goodsData.findById(basket.getIdGoods()));
        }
        TotalPrice totalPrice=totalPriceData.findByUserID(user.getIdUser());
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("goods", goodsList);

    }
    public void deleteGoods(long idGoods){
        User user = userData.findByEmail(getUserDetails().getUsername());
        Basket basket=basketData.findByIds(user.getIdUser(), idGoods);
        Goods goods=goodsData.findById(idGoods);
        TotalPrice totalPrice=totalPriceData.findByUserID(user.getIdUser());
        totalPrice.setSuma(totalPrice.getSuma()-goods.getPrice());
        totalPriceData.save(totalPrice);
        basketData.delete(basket);
    }

    public void getBuyMenu(long id,Model model){
        model.addAttribute(goodsData.findById(id));
    }
    public void goodToBasket(long idGoods){
        User user=userData.findByEmail(getUserDetails().getUsername());
        Goods goods=goodsData.findById(idGoods);
        TotalPrice totalPrice;
        Basket basket=Basket.builder()
                .idGoods(idGoods)
                .idUser(user.getIdUser())
                .build();
        if(totalPriceData.totalPriceExist(user.getIdUser())){
            totalPrice= totalPriceData.findByUserID(user.getIdUser());
            totalPrice.setSuma(totalPrice.getSuma()+goods.getPrice());
        }else {
            totalPrice=TotalPrice.builder()
                    .idUser(user.getIdUser())
                    .suma(goods.getPrice())
                    .build();
        }
        totalPriceData.save(totalPrice);
        basketData.save(basket);
    }
}
