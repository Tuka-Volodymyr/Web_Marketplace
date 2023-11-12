package com.example.web_marketplace.controllers;

import com.example.web_marketplace.model.dto.FilterDto;
import com.example.web_marketplace.model.entities.Goods;
import com.example.web_marketplace.service.GoodsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public String addCommodity(@ModelAttribute("goods") @Valid Goods goods,
                               BindingResult bindingResult,Model model,@RequestParam(value = "file",required = false) MultipartFile file){
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
    public String deleteCommodity(@RequestParam("idGoods") long idGoods, Model model){
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
        try {
            goodsService.getYourGoods(model);
            return "goods/getYourGoods";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "account/yourAccount";
        }
    }
    @GetMapping ("/find/goods")
    public String findGoods(@ModelAttribute("filterDto") FilterDto filterDto, HttpSession session, Model model){
        filterDto.setMaxPrice(999999);
        if(session.getAttribute("filter")!=null){
            FilterDto filter=(FilterDto) session.getAttribute("filter");
            filterDto.setCategory(filter.getCategory());
            filterDto.setMinPrice(filter.getMinPrice());
            filterDto.setMaxPrice(filter.getMaxPrice());
        }
        return "goods/foundGoods";
    }

    @PostMapping("get/found/goods")
    public String getCommodityByCategory(@ModelAttribute("filterDto") FilterDto filterDto, Model model, HttpSession session){
        try {
            if(filterDto.getCategory().isBlank()&& filterDto.getMaxPrice()<= filterDto.getMinPrice())
                return "redirect:/goods";
            goodsService.getFoundGoodsWithFilter(model, filterDto,session);
            return "goods/goods";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "goods/foundGoods";
        }
    }
    @GetMapping("/get/sortGoods/decrease")
    public String getSortGoodsDecrease(Model model){
        goodsService.getSortDecreaseByPrice(model);
        return "goods/goods";
    }
    @GetMapping("/goods")
    public String goods(Model model){
        try {
            goodsService.getGoods(model);
            return "goods/goods";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "goods/goods";
        }
    }
    @GetMapping("/get/redact/good")
    private String getRedactGood(@RequestParam("idGoods") long idGoods, Model model){
        goodsService.getRedactGood(idGoods,model);
        return "goods/redactGood";
    }
    @PostMapping(path = "/redact/good",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private String redactGood(@ModelAttribute("good") @Valid Goods good,
                              BindingResult bindingResult,Model model,
                              @RequestParam(value = "file",required = false) MultipartFile file){
        try {
            if(bindingResult.hasErrors()){
                System.out.println(bindingResult.getFieldError());
                return "goods/redactGood";
            }
            goodsService.redactGood(good,file);
            return "redirect:/your/goods";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "goods/redactGood";
        }
    }


}
