package com.example.web_marketplace.controllers;

import com.example.web_marketplace.model.entities.Rating;
import com.example.web_marketplace.exceptions.BadRequestException;
import com.example.web_marketplace.model.dto.ChangePassDto;
import com.example.web_marketplace.model.dto.EmailDto;
import com.example.web_marketplace.model.entities.Code;
import com.example.web_marketplace.model.entities.User;
import com.example.web_marketplace.service.GoodsService;
import com.example.web_marketplace.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;



    @GetMapping("/")
    public String login(){
        return "authorization/loginForm";
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
    public String getInfoSeller(@ModelAttribute("emailDto") EmailDto email){
        return "account/findSeller/emailFormForFindUser";
    }

    @PostMapping("/find/user")
    public String infoOfSeller(@ModelAttribute("emailDto") @Valid EmailDto email, BindingResult bindingResult,
                               Model model, @ModelAttribute("rating") Rating rating, HttpSession session){
        if(bindingResult.hasErrors())return "account/findSeller/emailFormForFindUser";
        try {
            userService.infoOfUser(email.getEmail(),model,session);
            userService.goodsToAttribute(model, email.getEmail());
            userService.ratingToAttribute(model,email.getEmail());
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
    public String getEmailForm(@ModelAttribute("emailDto") EmailDto email){
        return "account/changePassword/emailFormForChangePass";
    }
    @PostMapping("/send/code")
    public String sendCode(@ModelAttribute("emailDto") @Valid EmailDto email, BindingResult bindingResult, Model model, HttpSession session){
        if(bindingResult.hasErrors())return "account/changePassword/emailFormForChangePass";
        try {
            userService.sendCode(email.getEmail());
            session.setAttribute("email",email.getEmail());
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
    public String checkCode(@RequestParam("code") String code, Model model){
        try {
            userService.checkCode(code);
            return "redirect:/get/change/password";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "account/changePassword/codeForm";
        }

    }
    @GetMapping("/get/change/password")
    public String getChangePass(@ModelAttribute("changePassDto") ChangePassDto changePassDto, HttpSession session, Model model){
        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);
        return "account/changePassword/changePassword";
    }
    @PostMapping("/change/password")
    public String changePass(@ModelAttribute("changePassDto") @Valid ChangePassDto changePassDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())return "account/changePassword/changePassword";
        try {

            userService.changePass(changePassDto);
            return "redirect:/";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "account/changePassword/changePassword";
        }

    }
    @GetMapping("/get/account")
    public String yourAccount(Model model,HttpSession session){
        userService.infoOfUser(GoodsService.getUserDetails().getUsername(),model,session);
        userService.ratingToAttribute(model,GoodsService.getUserDetails().getUsername());
        return "account/yourAccount";
    }
    @GetMapping("/get/account/settings")
    public String getAccountSettings(){
        return "account/settings/settings";
    }
    @GetMapping("/send/code/without/emailDto")
    public String sendCodeWithoutEmailForm(HttpSession session){
        String email=GoodsService.getUserDetails().getUsername();
        userService.sendCode(email);
        session.setAttribute("email",email);
        return "redirect:/get/check/code";
    }

    @PostMapping("/evaluate")
    public String evaluate(@ModelAttribute("rating") Rating rating, HttpSession session,Model model){
        try {
            userService.evaluate(rating,session,model);
            return "account/findSeller/userInfo";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "account/findSeller/emailFormForFindUser";
        }

    }
}
