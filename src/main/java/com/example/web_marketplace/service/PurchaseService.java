package com.example.web_marketplace.service;
import java.util.stream.*;
import com.example.web_marketplace.model.entities.*;
import com.example.web_marketplace.repository.impl.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.example.web_marketplace.service.GoodsService.getUserDetails;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final GoodsRepositoryImplement goodsRepositoryImplement;
    private final UserRepositoryImplement userRepositoryImplement;
    private final BasketRepositoryImplement basketRepositoryImplement;
    private final TotalPriceRepositoryImplement totalPriceRepositoryImplement;
    private final OrderRepositoryImplement orderRepositoryImplement;
    public static final Sort sortOrder = Sort.by(Sort.Direction.DESC, "date");

    public void getBasket(Model model){
        User user= userRepositoryImplement.findByEmail(getUserDetails().getUsername());
        List<Basket> basketList= basketRepositoryImplement.findByUser(user.getIdUser());
        List<Goods> goodsList=new ArrayList<>();
        for(Basket basket:basketList){
            Goods goods= goodsRepositoryImplement.findById(basket.getIdGoods());
            String base64Image = Base64.getEncoder().encodeToString(goods.getBytePhoto());
            goods.setPhotoOfGood(base64Image);
            goodsList.add(goods);
        }
        TotalPrice totalPrice= totalPriceRepositoryImplement.findByUserID(user.getIdUser());
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("goods", goodsList);

    }
    public void deleteGoods(long idGoods){
        User user = userRepositoryImplement.findByEmail(getUserDetails().getUsername());
        List<Basket> basket= basketRepositoryImplement.findByIds(user.getIdUser(), idGoods);
            Goods goods= goodsRepositoryImplement.findById(idGoods);
            TotalPrice totalPrice= totalPriceRepositoryImplement.findByUserID(user.getIdUser());
            totalPrice.setSuma(totalPrice.getSuma()-goods.getPrice());
            totalPriceRepositoryImplement.save(totalPrice);
            basketRepositoryImplement.delete(basket.get(0));
    }
    public void deleteGoodsFromEachBasket(long idGoods){
        List<Basket> baskets= basketRepositoryImplement.findByGoodsId(idGoods);
        for(Basket basket:baskets) {
            TotalPrice totalPrice= totalPriceRepositoryImplement.findByUserID(basket.getIdUser());
            Goods goods= goodsRepositoryImplement.findById(basket.getIdGoods());
            totalPrice.setSuma(totalPrice.getSuma()-goods.getPrice());
            totalPriceRepositoryImplement.save(totalPrice);
        }
        basketRepositoryImplement.deleteList(baskets);
    }

    public void getBuyMenu(long id,Model model){
        model.addAttribute(goodsRepositoryImplement.findById(id));
    }
    public void goodToBasket(long idGoods){
        User user= userRepositoryImplement.findByEmail(getUserDetails().getUsername());
        Goods goods= goodsRepositoryImplement.findById(idGoods);
        TotalPrice totalPrice;
        Basket basket=Basket.builder()
                .idGoods(idGoods)
                .idUser(user.getIdUser())
                .build();
        if(totalPriceRepositoryImplement.totalPriceExist(user.getIdUser())){
            totalPrice= totalPriceRepositoryImplement.findByUserID(user.getIdUser());
            totalPrice.setSuma(totalPrice.getSuma()+goods.getPrice());
        }else {
            totalPrice=TotalPrice.builder()
                    .idUser(user.getIdUser())
                    .suma(goods.getPrice())
                    .build();
        }
        totalPriceRepositoryImplement.save(totalPrice);
        basketRepositoryImplement.save(basket);
    }
    public void getOrderMenu(Model model, Order order){
        User user= userRepositoryImplement.findByEmail(getUserDetails().getUsername());
        List<Basket> basketList= basketRepositoryImplement.findByUser(user.getIdUser());
        ArrayList<Goods> goodsArrayList=new ArrayList<>();
        for (Basket basket:basketList){
            Goods good= goodsRepositoryImplement.findById(basket.getIdGoods());
            String base64Image = Base64.getEncoder().encodeToString(good.getBytePhoto());
            good.setPhotoOfGood(base64Image);
            goodsArrayList.add(good);
        }

        model.addAttribute("goods",goodsArrayList);
        model.addAttribute("price", totalPriceRepositoryImplement.findByUserID(user.getIdUser()));
        ArrayList<String> nameOfGoods=new ArrayList<>();
        for(Goods good:goodsArrayList){
            nameOfGoods.add(good.getName());
        }
        order.setNameOfGood(nameOfGoods);
    }
    @Transactional
    public void buy(Order order){
        User user= userRepositoryImplement.findByEmail(getUserDetails().getUsername());
        List<Basket> basket= basketRepositoryImplement.findByUser(user.getIdUser());

        TotalPrice totalPrice= totalPriceRepositoryImplement.findByUserID(user.getIdUser());
        List<String> goodsList=basket.stream()
                .map(someBasket-> goodsRepositoryImplement.findById(someBasket.getIdGoods()).getName())
                .collect(Collectors.toList());
        order.setIdBuyerAccount(user.getIdUser());
        order.setDate(LocalDate.now());
        order.setPrice(totalPrice.getSuma());
        order.setNameOfGood(goodsList);
        orderRepositoryImplement.save(order);
        totalPriceRepositoryImplement.delete(totalPrice);
        basketRepositoryImplement.deleteList(basket);
    }
    public void getHistoryOrder(Model model){
        User user= userRepositoryImplement.findByEmail(getUserDetails().getUsername());
        model.addAttribute("order", orderRepositoryImplement.findByUserId(user.getIdUser(),sortOrder));
    }
}
