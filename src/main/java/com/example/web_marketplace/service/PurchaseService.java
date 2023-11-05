package com.example.web_marketplace.service;
import java.util.stream.*;
import com.example.web_marketplace.data.*;
import com.example.web_marketplace.entities.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.example.web_marketplace.service.GoodsService.getUserDetails;
import static com.example.web_marketplace.service.GoodsService.sort;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final GoodsData goodsData;
    private final UserData userData;
    private final BasketData basketData;
    private final TotalPriceData totalPriceData;
    private final OrderData orderData;
    public static final Sort sortOrder = Sort.by(Sort.Direction.DESC, "date");

    public void getBasket(Model model){
        User user=userData.findByEmail(getUserDetails().getUsername());
        List<Basket> basketList=basketData.findByUser(user.getIdUser());
        List<Goods> goodsList=new ArrayList<>();
        for(Basket basket:basketList){
            Goods goods=goodsData.findById(basket.getIdGoods());
            String base64Image = Base64.getEncoder().encodeToString(goods.getBytePhoto());
            goods.setPhotoOfGood(base64Image);
            goodsList.add(goods);
        }
        TotalPrice totalPrice=totalPriceData.findByUserID(user.getIdUser());
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("goods", goodsList);

    }
    public void deleteGoods(long idGoods){
        User user = userData.findByEmail(getUserDetails().getUsername());
        List<Basket> basket=basketData.findByIds(user.getIdUser(), idGoods);
            Goods goods=goodsData.findById(idGoods);
            TotalPrice totalPrice=totalPriceData.findByUserID(user.getIdUser());
            totalPrice.setSuma(totalPrice.getSuma()-goods.getPrice());
            totalPriceData.save(totalPrice);
            basketData.delete(basket.get(0));
    }
    public void deleteGoodsFromEachBasket(long idGoods){
        List<Basket> baskets=basketData.findByGoodsId(idGoods);
        for(Basket basket:baskets) {
            TotalPrice totalPrice=totalPriceData.findByUserID(basket.getIdUser());
            Goods goods=goodsData.findById(basket.getIdGoods());
            totalPrice.setSuma(totalPrice.getSuma()-goods.getPrice());
            totalPriceData.save(totalPrice);
        }
        basketData.deleteList(baskets);
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
        for (Basket basket:basketList){
            Goods good=goodsData.findById(basket.getIdGoods());
            String base64Image = Base64.getEncoder().encodeToString(good.getBytePhoto());
            good.setPhotoOfGood(base64Image);
            goodsArrayList.add(good);
        }

        model.addAttribute("goods",goodsArrayList);
        model.addAttribute("price",totalPriceData.findByUserID(user.getIdUser()));
        ArrayList<String> nameOfGoods=new ArrayList<>();
        for(Goods good:goodsArrayList){
            nameOfGoods.add(good.getName());
        }
        order.setNameOfGood(nameOfGoods);
    }
    @Transactional
    public void buy(Order order){
        User user=userData.findByEmail(getUserDetails().getUsername());
        List<Basket> basket=basketData.findByUser(user.getIdUser());

        TotalPrice totalPrice=totalPriceData.findByUserID(user.getIdUser());
        List<String> goodsList=basket.stream()
                .map(someBasket->goodsData.findById(someBasket.getIdGoods()).getName())
                .collect(Collectors.toList());
        order.setIdBuyerAccount(user.getIdUser());
        order.setDate(LocalDate.now());
        order.setPrice(totalPrice.getSuma());
        order.setNameOfGood(goodsList);
        orderData.save(order);
        totalPriceData.delete(totalPrice);
        basketData.deleteList(basket);
    }
    public void getHistoryOrder(Model model){
        User user=userData.findByEmail(getUserDetails().getUsername());
        model.addAttribute("order",orderData.findByUserId(user.getIdUser(),sortOrder));
    }
}
