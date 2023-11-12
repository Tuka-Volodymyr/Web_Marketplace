package com.example.web_marketplace.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getMenuTest_success() throws Exception {
        mockMvc.perform(get("/get/buy/menu")
                .param("idGoods","2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("goods/buy/buyMenu"));
    }
    @Test
    public void getMenuTest_fail() throws Exception {
        mockMvc.perform(get("/get/buy/menu")
                        .param("idGoods","999999999"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("goods/goods"));
    }


}
