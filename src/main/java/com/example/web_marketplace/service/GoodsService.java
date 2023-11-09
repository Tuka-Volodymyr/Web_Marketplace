package com.example.web_marketplace.service;

import com.example.web_marketplace.data.BasketData;
import com.example.web_marketplace.data.GoodsData;
import com.example.web_marketplace.data.UserData;
import com.example.web_marketplace.forms.FilterForm;
import com.example.web_marketplace.entities.Goods;
import com.example.web_marketplace.entities.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsData goodsData;
    private final UserData userData;
    private final PurchaseService purchaseService;

    public static final Sort sort = Sort.by(Sort.Direction.ASC, "price");
    public void addGoods(Goods goods,MultipartFile multipartFile) throws IOException {
        UserDetails userDetails=getUserDetails();
        goods.setLocalDate(LocalDate.now());
        goods.setUserEmail(userDetails.getUsername());
        if(!multipartFile.isEmpty()){
            byte[] photoBytes = multipartFile.getBytes();

            goods.setBytePhoto(photoBytes);
        }
        goodsData.save(goods);
    }
    public void deleteGoods(long idGoods){
        Goods goods=goodsData.findById(idGoods);
        //Має удаляти у всіх клієнтів!!
//        purchaseService.deleteGoods(goods.getIdGood());
        purchaseService.deleteGoodsFromEachBasket(idGoods);
        goodsData.delete(goods);


    }
    public void getGoods(Model model){
        List<Goods> goodsList=goodsData.findAll(sort);
        for(Goods good:goodsList){
            String base64Image = Base64.getEncoder().encodeToString(good.getBytePhoto());
            good.setPhotoOfGood(base64Image);
        }
       model.addAttribute("goods",goodsList);
    }
    public void getYourGoods(Model model){
        UserDetails userDetails=getUserDetails();
        List<Goods> list=goodsData.findByEmail(userDetails.getUsername(),sort);
        for(Goods good:list){
            String base64Image = Base64.getEncoder().encodeToString(good.getBytePhoto());
            good.setPhotoOfGood(base64Image);
        }
        model.addAttribute("goods",list);
    }
    public void getFoundGoodsWithFilter(Model model, FilterForm filter, HttpSession session){
        List<Goods> goodsList=new ArrayList<>();
        if (!filter.getCategory().isBlank() && filter.getMaxPrice()> filter.getMinPrice()) {
            goodsList=goodsData.findByMinPriceAndCategoryAndMaxPrice(filter,sort);
        }else if (!filter.getCategory().isBlank() ) {
            goodsList=goodsData.findByMinPriceAndCategory(filter,sort);
        } else if (filter.getMaxPrice() > filter.getMinPrice()) {
            goodsList=goodsData.findByMinPriceAndMax(filter,sort);
        }else if (filter.getMinPrice() > 0){
            goodsList=goodsData.findByMinPrice(filter.getMinPrice(),sort);
        }
        for(Goods good:goodsList){
            String base64Image = Base64.getEncoder().encodeToString(good.getBytePhoto());
            good.setPhotoOfGood(base64Image);
        }
        session.setAttribute("filter",filter);
        model.addAttribute("goods",goodsList);
    }
    public void getSortDecreaseByPrice(Model model){
//        model.addAttribute("goods",)
    }
    public void getSortIncreaseByPrice(Model model){

    }
    public void getRedactGood(Long idGood,Model model){
        Goods good=goodsData.findById(idGood);
        model.addAttribute("good",good);
    }
    public void redactGood(Goods goods,MultipartFile multipartFile) throws IOException {
        if(!multipartFile.isEmpty()){
            byte[] photoBytes = multipartFile.getBytes();

            goods.setBytePhoto(photoBytes);
        }
        goodsData.save(goods);
    }

    public static UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
