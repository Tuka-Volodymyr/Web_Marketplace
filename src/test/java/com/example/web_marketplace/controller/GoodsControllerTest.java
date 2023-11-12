package com.example.web_marketplace.controller;

import com.example.web_marketplace.controllers.GoodsController;
import com.example.web_marketplace.controllers.UserController;
import com.example.web_marketplace.repository.impl.GoodsRepositoryImplement;
import com.example.web_marketplace.model.entities.Goods;

import com.example.web_marketplace.model.dto.FilterDto;
import com.example.web_marketplace.service.GoodsService;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
public class GoodsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GoodsController goodsController;

    @Autowired
    private UserController userController;

    @Autowired
    private GoodsRepositoryImplement goodsRepositoryImplement;


//    Goods goodSuccess=new Goods(99L,"Tuka","22 year","people",3000,null,null,LocalDate.ofEpochDay(2023- 6 -23),"tuka@gmail.com");

    @Test
    public void loginTest_success() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("authorization/loginForm"));
    }
    @Test
    public void newCommodityTest_success()throws Exception{
        Goods goods=new Goods();
        this.mockMvc.perform(get("/new/goods")
                        .flashAttr("goods", goods))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("goods/addGoods"));
    }
    @Test
    @Transactional
    public void addCommodityTest_success()throws Exception{
        Goods goods=Goods.builder()
                .name("Iphone 8")
                .description("256 GB")
                .category("technic")
                .price(8000)
                .localDate(LocalDate.ofEpochDay(2023- 6 -23))
                .userEmail("tukavolody@gmail.com")
                .build();

        MockMultipartFile file = new MockMultipartFile(
                "file",            // parameter name expected by the controller
                "test.txt",        // original file name
                MediaType.TEXT_PLAIN_VALUE, // content type
                "Hello, World!".getBytes()  // content as byte array
        );
        mockMvc.perform(MockMvcRequestBuilders.multipart("/add/goods")
                        .file(file)
                        .flashAttr("goods", goods)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/your/goods"));

    }
    @Test
    public void addCommodityTest_fail()throws Exception{
        Goods goods=Goods.builder()
                .userEmail("tukavolody@gmail.com")
                .build();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        mockMvc.perform(MockMvcRequestBuilders.multipart("/add/goods")
                        .file(file)
                        .flashAttr("goods", goods)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("goods/addGoods"));
    }
    @Test

    @AfterAll
    public void deleteCommodity_success() throws Exception {
        List<Goods> good= goodsRepositoryImplement.findByEmail("user", GoodsService.sort);
        for(Goods someGood:good){
            mockMvc.perform(MockMvcRequestBuilders.post("/delete/goods")
                            .param("idGoods", String.valueOf(someGood.getIdGood())))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.redirectedUrl("/your/goods"));
        }
    }
    @Test
    public void deleteCommodity_fail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/delete/goods")
                        .param("idGoods", String.valueOf(180)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("goods/getYourGoods"));
    }

    @Test
    public void getCommodityByCategory_success() throws Exception {
        FilterDto filterDto = FilterDto.builder()
                .category("technic")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/get/found/goods")
                        .flashAttr("filterDto", filterDto))
//                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("goods/goods"));
    }
    @Test
    public void getCommodityByCategory_fail() throws Exception {
        FilterDto filterDto = FilterDto.builder()
                .maxPrice(100)
                .minPrice(2000)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/get/found/goods")
                        .flashAttr("filterForm", filterDto))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("goods/foundGoods"));
    }



}
