package com.example.web_marketplace.service;


import com.example.web_marketplace.data.GoodsData;
import com.example.web_marketplace.data.UserData;
import org.junit.Before;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

public class GoodsServiceTest {
    private GoodsService goodsService;

    private GoodsData goodsDataMock;
    private UserData userDataMock;
    private PurchaseService purchaseServiceMock;
    @Before
    public void setUp(){
        goodsDataMock=mock(GoodsData.class);
        userDataMock=mock(UserData.class);
        purchaseServiceMock=mock(PurchaseService.class);
        goodsService=new GoodsService(goodsDataMock,userDataMock,purchaseServiceMock);
    }

}
