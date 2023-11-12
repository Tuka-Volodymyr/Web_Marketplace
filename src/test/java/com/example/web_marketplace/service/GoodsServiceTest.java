package com.example.web_marketplace.service;


import com.example.web_marketplace.repository.impl.GoodsRepositoryImplement;
import com.example.web_marketplace.repository.impl.UserRepositoryImplement;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class GoodsServiceTest {
    private GoodsService goodsService;

    private GoodsRepositoryImplement goodsRepositoryImplementMock;
    private UserRepositoryImplement userRepositoryImplementMock;
    private PurchaseService purchaseServiceMock;
    @Before
    public void setUp(){
        goodsRepositoryImplementMock =mock(GoodsRepositoryImplement.class);
        userRepositoryImplementMock =mock(UserRepositoryImplement.class);
        purchaseServiceMock=mock(PurchaseService.class);
        goodsService=new GoodsService(goodsRepositoryImplementMock, userRepositoryImplementMock,purchaseServiceMock);
    }

}
