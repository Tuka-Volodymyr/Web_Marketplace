package com.example.web_marketplace.controllers;

import com.example.web_marketplace.dto.EmailForm;
import com.example.web_marketplace.entities.User;
import com.example.web_marketplace.service.UserService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String login(){
        return "authorization/login";
    }

    @GetMapping("/new/user")
    public String newSeller(@ModelAttribute("user") User user){
        return "authorization/register";
    }
    @PostMapping("/add/user")
    public String createSeller(@ModelAttribute("user") @Valid User user,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors())return "authorization/register";
        userService.addUser(user);
        return "redirect:/login";
    }
    @GetMapping("/get/user")
    public String getInfoSeller(@ModelAttribute("emailForm") EmailForm email){
        return "account/email";
    }

    @PostMapping("/info/user")
    public String infoOfSeller(@ModelAttribute("emailForm") @Valid EmailForm email, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())return "account/email";
        userService.infoOfUser(email.getEmail(),model);
        return "account/userInfo";
    }
    @GetMapping("/main")
    public String mainPage(){
        return "account/main";
    }
}
