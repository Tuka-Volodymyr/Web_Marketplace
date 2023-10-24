package com.example.web_marketplace.controllers;

import com.example.web_marketplace.entities.User;
import com.example.web_marketplace.forms.FilterForm;
import com.example.web_marketplace.forms.IdGoods;
import com.example.web_marketplace.entities.Goods;
import com.example.web_marketplace.service.GoodsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

@Controller
@Data
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;



    @GetMapping("/new/goods")
    public String newCommodity(@ModelAttribute("goods") Goods goods){
        return "goods/addGoods";
    }

    @PostMapping(path = "/add/goods",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addCommodity(@ModelAttribute("goods") @Valid Goods goods, BindingResult bindingResult,Model model,@RequestParam(value = "file",required = false) MultipartFile file){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getFieldError());
            return "goods/addGoods";
        }
        try {
            goodsService.addGoods(goods,file);
            return "redirect:/your/goods";
        } catch (IOException e) {
            model.addAttribute("error",e.getMessage());
            return "redirect:/new/goods";
        }
    }

    @PostMapping("/delete/goods")
    public String deleteCommodity(@RequestParam("idGoods") long idGoods, Model model, HttpSession session){
        try {
            goodsService.deleteGoods(idGoods);
            return "redirect:/your/goods";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "goods/getYourGoods";
        }

    }
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
        if(filterForm.getCategory().isBlank()&&filterForm.getMaxPrice()<=filterForm.getMinPrice()&&filterForm.getMaxPrice()!=0)
            return "redirect:/goods";
        goodsService.getFoundGoodsWithFilter(model, filterForm);
        return "goods/goods";
    }
    @GetMapping("/get/sortGoods/decrease")
    public String getSortGoodsDecrease(Model model){
        goodsService.getSortDecreaseByPrice(model);
        return "goods/goods";
    }
    @GetMapping("/goods")
    public String goods(Model model){
        goodsService.getGoods(model);
        return "goods/goods";
    }

}
