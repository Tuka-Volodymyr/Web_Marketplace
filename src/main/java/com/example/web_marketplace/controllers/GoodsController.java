package com.example.web_marketplace.controllers;

import com.example.web_marketplace.dto.FilterForm;
import com.example.web_marketplace.entities.Goods;
import com.example.web_marketplace.service.GoodsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class GoodsController {
    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    //make general goods html and  get new good , delete ,your goods,all goods ADD sort
    @GetMapping("/new/goods")
    public String newCommodity(@ModelAttribute("goods") Goods goods){
        return "goods/addGoods";
    }

    @PostMapping("/add/goods")
    public String addCommodity(@ModelAttribute("goods") @Valid Goods goods, BindingResult bindingResult){
        if(bindingResult.hasErrors())return "goods/addGoods";
        goodsService.addGoods(goods);
        return "redirect:/goods";
    }
//    @DeleteMapping("/delete/goods")
//    public ResponseEntity<?> deleteCommodity(@RequestParam String name){
//        return new ResponseEntity<>(goodsService.deleteGoods(name),HttpStatus.OK);
//    } in my goods
    @GetMapping("/your/goods")
    public String getYourGoods(Model model){
        goodsService.getYourGoods(model);
        return "goods/getYourGoods";
    }
    @GetMapping ("/find/goods")
    public String findGoods(@ModelAttribute("filterForm") FilterForm filterForm){
        return "goods/foundGoods";
    }

    @PostMapping("get/found/goods")
    public String getCommodityByCategory(@ModelAttribute("filterForm") FilterForm filterForm, Model model){
        if(filterForm.getCategory().isBlank()&&filterForm.getMaxPrice()<=filterForm.getMinPrice())
            return "redirect:/goods";
        goodsService.getFoundGoodsWithFilter(model, filterForm);
        return "goods/goods";
    }
    @GetMapping("/goods")
    public String goods(Model model){
        goodsService.getGoods(model);
        return "goods/goods";
    }

}
