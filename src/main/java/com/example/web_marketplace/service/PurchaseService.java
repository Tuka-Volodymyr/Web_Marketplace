package com.example.web_marketplace.service;

import com.example.web_marketplace.data.*;
import com.example.web_marketplace.entities.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.web_marketplace.service.GoodsService.getUserDetails;
import static com.example.web_marketplace.service.GoodsService.sort;

@Service
public class PurchaseService {
    private final GoodsData goodsData;
    private final UserData userData;
    private final BasketData basketData;
    private final TotalPriceData totalPriceData;
    private final OrderData orderData;

    public PurchaseService(GoodsData goodsData, UserData userData, BasketData basketData, TotalPriceData totalPriceData, OrderData orderData) {
        this.goodsData = goodsData;
        this.userData=userData;
        this.basketData = basketData;
        this.totalPriceData = totalPriceData;
        this.orderData = orderData;
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
    public void getOrderMenu(Model model,Order order){
        User user=userData.findByEmail(getUserDetails().getUsername());
        List<Basket> basketList=basketData.findByUser(user.getIdUser());
        ArrayList<Goods> goodsArrayList=new ArrayList<>();
        for (Basket basket:basketList)
            goodsArrayList.add(goodsData.findById(basket.getIdGoods()));
        model.addAttribute("goods",goodsArrayList);
        model.addAttribute("price",totalPriceData.findByUserID(user.getIdUser()));
        order.setService(goodsArrayList);
    }
    @Transactional
    public void buy(Order order){
        User user=userData.findByEmail(getUserDetails().getUsername());
        List<Basket> basket=basketData.findByUser(user.getIdUser());
        List<Goods> goodsList=new ArrayList<>();
        TotalPrice totalPrice=totalPriceData.findByUserID(user.getIdUser());
        for(Basket someBasket:basket){
            goodsList.add(goodsData.findById(someBasket.getIdGoods()));
        }
        order.setIdBuyerAccount(user.getIdUser());
        order.setDate(LocalDate.now());
        order.setPrice(totalPrice.getSuma());
        order.setService(goodsList);
        orderData.save(order);
        totalPriceData.delete(totalPrice);
        basketData.deleteList(basket);
    }
    public void getHistoryOrder(Model model){
        User user=userData.findByEmail(getUserDetails().getUsername());
        model.addAttribute("order",orderData.findByUserId(user.getIdUser()));



    }
}
