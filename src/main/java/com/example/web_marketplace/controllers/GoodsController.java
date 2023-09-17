package com.example.web_marketplace.controllers;

import com.example.web_marketplace.entities.Goods;
import com.example.web_marketplace.service.GoodsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class GoodsController {
    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }
    @GetMapping("/new/goods")
    public String newCommodity(@ModelAttribute("goods") Goods goods){
        return "goods/addGoods";
    }

    @PostMapping("/add/goods")
    public String addCommodity(@ModelAttribute("goods") @Valid Goods goods, BindingResult bindingResult){
        if(bindingResult.hasErrors())return "goods/addGoods";
        goodsService.addGoods(goods);
        return "redirect:/api/main";
    }
//    @DeleteMapping("/delete/goods")
//    public ResponseEntity<?> deleteCommodity(@RequestParam String name){
//        return new ResponseEntity<>(goodsService.deleteGoods(name),HttpStatus.OK);
//    }
    @GetMapping("/your/goods")
    public String getYourGoods(Model model){
        goodsService.getYourGoods(model);
        return "goods/getGoods";
    }

//    @GetMapping("/all/goods")
//    public ResponseEntity<?> getCommodityByCategory(@RequestParam(required=false) String category){
//        return new ResponseEntity<>(goodsService.getGoods(category),HttpStatus.OK);
//    }
}
