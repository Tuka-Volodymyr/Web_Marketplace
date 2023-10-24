package com.example.web_marketplace.controllers;

import com.example.web_marketplace.entities.Order;
import com.example.web_marketplace.service.PurchaseService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Value("${stripe.secretKey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
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
    public String getBasket(Model model, HttpSession session){
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


    @GetMapping("/get/order/menu")
    public String getOrderMenu(@ModelAttribute("order")Order order,Model model){
        try {
            purchaseService.getOrderMenu(model,order);
            return "goods/buy/orderForm";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "redirect:/get/basket";
        }

    }
    @Transactional
    @PostMapping("/buy")
    public String buy(@ModelAttribute("order")Order order,Model model){
        try {
            purchaseService.buy(order);
            if(order.getPayment().equals("Prepayment")){
                return "redirect:/get/payment";
            }
            return "redirect:/get/history/order";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "redirect:/get/order/menu";
        }

    }
    @GetMapping("/get/payment")
    public String getProcessPayment(){
        return "goods/buy/payment";
    }
    @Transactional
    @PostMapping("/payment")
    public String processPayment(
            @RequestParam("cardNumber") String cardNumber,
            @RequestParam("expiryDate") String expiryDate,
            @RequestParam("cvv") String cvv,
            Model model
    ) {
//        try {


//            int amountInCents = 1000;
//            String currency = "usd";
//
//            Charge charge = Charge.create(
//                    new ChargeCreateParams.Builder()
//                            .setAmount((long) amountInCents)
//                            .setCurrency(currency)
//                            .setSource(cardNumber)
//                            .build()
//            );


            System.out.println("Sussecce");
            model.addAttribute( "message", "Success");
            return "redirect:/get/history/order";

////        } catch (StripeException e) {
//            model.addAttribute("error", "Foul");
//            return "redirect:/get/payment";
////        }
    }

    @GetMapping("/get/history/order")
    public String getHistoryOrder(Model model){
        purchaseService.getHistoryOrder(model);
        return "goods/buy/orderHistory";
    }

}
