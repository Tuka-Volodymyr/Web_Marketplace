package com.example.web_marketplace.controller;

import com.example.web_marketplace.controllers.GoodsController;
import com.example.web_marketplace.forms.IdGoods;
import com.example.web_marketplace.service.GoodsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GoodsControllerTest {
    private GoodsService goodsServiceMock;

    private GoodsController goodsController;

    @Before
    public void setUp(){
        goodsServiceMock=mock(GoodsService.class);
        goodsController=new GoodsController(goodsServiceMock);
    }

//    @Test
//    public void deleteCommodityTest(){
//        IdGoods idGoods=IdGoods.builder()
//                .idGoods(3)
//                .build();
//        Model model = null;
//        String str = goodsController.deleteCommodity(,model);
//        assertEquals("redirect:/your/goods",str);
//
//    }

}
