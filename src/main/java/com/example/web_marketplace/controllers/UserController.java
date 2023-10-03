package com.example.web_marketplace.controllers;

import com.example.web_marketplace.exceptions.BadRequestException;
import com.example.web_marketplace.forms.ChangePass;
import com.example.web_marketplace.forms.EmailForm;
import com.example.web_marketplace.entities.Code;
import com.example.web_marketplace.entities.User;
import com.example.web_marketplace.service.GoodsService;
import com.example.web_marketplace.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.mail.SimpleMailMessage;
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
        try {
            userService.addUser(user);
            return "redirect:/login";
        } catch (BadRequestException e) {
            model.addAttribute("error", e.getMessage());
            return "authorization/register";
        }

    }
    @GetMapping("/get/user")
    public String getInfoSeller(@ModelAttribute("emailForm") EmailForm email){
        return "account/findSeller/emailFormForFindUser";
    }

    @PostMapping("/find/user")
    public String infoOfSeller(@ModelAttribute("emailForm") @Valid EmailForm email, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())return "account/findSeller/emailFormForFindUser";
        try {
            userService.infoOfUser(email.getEmail(),model);
            userService.goodsToAttribute(model, email.getEmail());
            return "account/findSeller/userInfo";
        } catch (BadRequestException e) {
            model.addAttribute("error", e.getMessage());
            return "account/findSeller/emailFormForFindUser";
        }

    }



    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }



    @GetMapping("/get/send/code")
    public String getEmailForm(@ModelAttribute("emailForm") EmailForm email){
        return "account/changePassword/emailFormForChangePass";
    }
    @PostMapping("/send/code")
    public String sendCode(@ModelAttribute("emailForm") @Valid EmailForm email, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())return "account/changePassword/emailFormForChangePass";
        try {

            userService.sendCode(email.getEmail());
            model.addAttribute("email",email);
            return "redirect:/get/check/code";
        } catch (BadRequestException e) {
            model.addAttribute("error", e.getMessage());
            return "account/changePassword/emailFormForChangePass";
        }

    }
    @GetMapping("/get/check/code")
    public String getCheckCode(@ModelAttribute("code") Code code){
        return "account/changePassword/codeForm";
    }
    @PostMapping("/check/code")
    public String checkCode(@ModelAttribute("code") Code code, HttpSession session, Model model){
        try {
            userService.checkCode(code,session);
            return "redirect:/get/change/password";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "account/changePassword/codeForm";
        }

    }
    @GetMapping("/get/change/password")
    public String getChangePass(@ModelAttribute("changePass") ChangePass changePass,HttpSession session, Model model){
        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);
        return "account/changePassword/changePassword";
    }
    @PostMapping("/change/password")
    public String changePass(@ModelAttribute("changePass") @Valid ChangePass changePass, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors())return "account/changePassword/changePassword";
        try {

            userService.changePass(changePass);
            return "redirect:/";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "account/changePassword/changePassword";
        }

    }
    @GetMapping("/get/account")
    public String yourAccount(Model model){
        userService.infoOfUser(GoodsService.getUserDetails().getUsername(),model);
        return "account/yourAccount";

    }



}
