package com.example.web_marketplace.controllers;

import com.example.web_marketplace.entities.Order;
import com.example.web_marketplace.service.PurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PurchaseController {
    private final PurchaseService purchaseService;
    public PurchaseController(PurchaseService purchaseService){
        this.purchaseService=purchaseService;

    }
    @GetMapping("/get/buy/menu")
    public String getMenu(@RequestParam("idGoods") long idGoods, Model model){
        purchaseService.getBuyMenu(idGoods,model);
        return "goods/buy/buyMenu";
    }
    @GetMapping("/get/add/to/basket")
    public String getGoodToBasket(@RequestParam("idGoods") long idGoods){
        purchaseService.goodToBasket(idGoods);
        return "redirect:/goods";
    }
    @GetMapping("/get/basket")
    public String getBasket(Model model){
        try {
            purchaseService.getBasket(model);
            return "goods/buy/basket";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "goods/buy/basket";
        }
    }

    @PostMapping("/basket/delete/goods")
    public String deleteGoodFromBasket(@RequestParam("idGoods") long idGoods, Model model){
        try {
            purchaseService.deleteGoods(idGoods);
            return "redirect:/get/basket";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "redirect:/get/basket/delete/goods";
        }
    }



    //History buying, rating
    @GetMapping("/get/order/menu")
    public String getOrderMenu(@ModelAttribute("order")Order order,Model model){
        purchaseService.getOrderMenu(model,order);
        return "goods/buy/orderForm";
    }
    @PostMapping("/buy")
    public String buy(@ModelAttribute("order")Order order){
        purchaseService.buy(order);
        return "redirect:/goods";
    }
    @GetMapping("/get/history/order")
    public String getHistoryOrder(Model model){
        purchaseService.getHistoryOrder(model);
        return "goods/buy/orderHistory";
    }
}
