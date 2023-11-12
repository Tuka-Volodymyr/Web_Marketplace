package com.example.web_marketplace.controller;

import com.example.web_marketplace.model.entities.Rating;
import com.example.web_marketplace.model.entities.User;
import com.example.web_marketplace.model.dto.EmailDto;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    public void loginTest_success() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("authorization/loginForm"));
    }
    @Test
    @Transactional
    public void createSeller_success() throws Exception {
        User user=User.builder()
                .fullName("Tuka Volodymyr")
                .email("t@gmail.com")
                .password("3456666")
                .phone("+380962000394")
                .build();
        this.mockMvc.perform(post("/add/user")
                        .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }
    @Test
    public void createSeller_fail() throws Exception {
        User user=User.builder()
                .fullName("Tuka Volodymyr")
                .email("Tuka@gmail.com")
                .password("3456666")
//                .phone("+380962000394")
                .build();
        this.mockMvc.perform(post("/add/user")
                        .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("authorization/register"));
    }
    @Test
    public void infoOfSeller_success() throws Exception {
        Rating rating=Rating.builder()
                .idSeller(2L)
                .lastRating(2)
                .build();
        EmailDto emailDto = EmailDto.builder()
                .email("tuka@gmail.com")
                .build();
        this.mockMvc.perform(post("/find/user")
                        .flashAttr("emailDto", emailDto)
                        .flashAttr("rating", rating))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("account/findSeller/userInfo"));
    }
    @Test
    public void infoOfSeller_fail() throws Exception {
        Rating rating=Rating.builder()
                .idSeller(2L)
                .lastRating(2)
                .build();
        EmailDto emailDto = EmailDto.builder()
                .email("ta@gmail.com")
                .build();
        this.mockMvc.perform(post("/find/user")
                        .flashAttr("emailForm", emailDto)
                        .flashAttr("rating", rating))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("account/findSeller/emailFormForFindUser"));
    }

//    @Test
//    public void evaluate_success() throws Exception {
//        MockHttpSession mockSession = new MockHttpSession();
//        mockSession.setAttribute("idSeller", 1L);
//        Rating rating=Rating.builder()
//                .rating(new TreeMap<>())
//                .lastRating(3)
//                .build();
//        this.mockMvc.perform(post("/evaluate")
//                        .session(mockSession)
//                        .flashAttr("rating", rating))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/get/user"));
//    }


}
