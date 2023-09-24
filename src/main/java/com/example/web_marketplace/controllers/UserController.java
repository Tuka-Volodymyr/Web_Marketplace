package com.example.web_marketplace.controllers;

import com.example.web_marketplace.dto.ChangePass;
import com.example.web_marketplace.dto.EmailForm;
import com.example.web_marketplace.entities.Code;
import com.example.web_marketplace.entities.User;
import com.example.web_marketplace.service.UserService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWW Write account settings!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @GetMapping("/")
    public String login(){
        return "authorization/index";
    }
    @GetMapping("/login")
    public String loginForm(){
        return "authorization/loginForm";
    }

    @GetMapping("/new/user")
    public String newSeller(@ModelAttribute("user") User user){
        return "authorization/register";
    }
    @PostMapping("/add/user")
    public String createSeller(@ModelAttribute("user") @Valid User user,
                               BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())return "authorization/register";
        return userService.addUser(user,model);
    }
    @GetMapping("/get/user")
    public String getInfoSeller(@ModelAttribute("emailForm") EmailForm email){
        return "account/emailForm";
    }

    @PostMapping("/info/user")
    public String infoOfSeller(@ModelAttribute("emailForm") @Valid EmailForm email, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())return "account/emailForm";
        return userService.infoOfUser(email.getEmail(),model);
    }
    @GetMapping("/main")
    public String mainPage(){
        return "account/main";
    }
    @GetMapping("/get/send/code")
    public String getEmailForm(@ModelAttribute("emailForm") EmailForm email){
        return "account/changePassword/emailFormForChangePass";
    }
    @PostMapping("/send/code")
    public String sendCode(@ModelAttribute("emailForm") @Valid EmailForm email, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())return "account/changePassword/emailFormForChangePass";
        return userService.sendCode(email.getEmail(),model);
    }
    @GetMapping("/get/check/code")
    public String getCheckCode(@ModelAttribute("code") Code code){
        return "account/changePassword/codeForm";
    }
    @PostMapping("/check/code")
    public String checkCode(@ModelAttribute("code") Code code, Model model){
        return userService.checkCode(code,model);
    }
    @GetMapping("/get/change/password")
    public String getChangePass(@ModelAttribute("changePass") ChangePass changePass){
        return "account/changePassword/changePassword";
    }
    @PostMapping("/change/password")
    public String changePass(@ModelAttribute("changePass") @Valid ChangePass changePass, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors())return "account/changePassword/changePassword";
        return userService.changePass(changePass,model);
    }

}
