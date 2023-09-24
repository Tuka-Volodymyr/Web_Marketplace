package com.example.web_marketplace.service;

import com.example.web_marketplace.data.CodeData;
import com.example.web_marketplace.data.UserData;
import com.example.web_marketplace.dto.ChangePass;
import com.example.web_marketplace.entities.Code;
import com.example.web_marketplace.entities.User;
import com.example.web_marketplace.exceptions.BadRequestException;
import com.example.web_marketplace.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
public class UserService {
    private final UserData userData;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final CodeData codeData;
    public UserService(UserData userData, PasswordEncoder passwordEncoder, CodeData codeData, JavaMailSender javaMailSender) {
        this.passwordEncoder=passwordEncoder;
        this.userData = userData;
        this.codeData=codeData;
        this.javaMailSender = javaMailSender;
    }
    public String addUser(User user, Model model){
        try {
            if(userData.isExist(user.getEmail()))throw new BadRequestException(
                    "Email has already used!"
            );
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userData.save(user);
            return "authorization/login";
        } catch (BadRequestException e) {
            model.addAttribute("error", e.getMessage());
            return "authorization/register";
        }
    }
    public String infoOfUser(String email, Model model){
        try {
            model.addAttribute("user", userData.findByEmail(email));
            return "account/userInfo";
        } catch (BadRequestException e) {
            model.addAttribute("error", e.getMessage());
            return "account/emailForm";
        }
    }
    public String sendCode(String email, Model model){
        try {
            User user= userData.findByEmail(email);
            if(codeData.findCodeByAccountId(user.getId()).isPresent())
                codeData.deleteCode(codeData.findCodeByAccountId(user.getId()).get());
            Code code=new Code(user.getId());
            codeData.saveCode(code);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset");
            message.setText("Code: "+code.getCode());
            javaMailSender.send(message);
            return "redirect:/get/check/code";
        } catch (BadRequestException e) {
            model.addAttribute("error", e.getMessage());
            return "account/changePassword/emailFormForChangePass";
        }
    }
    public String checkCode(Code code,Model model){
        try {
            codeData.findByCode(code.getCode());
            return "redirect:/get/change/password";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "account/changePassword/codeForm";
        }
    }
    public String changePass(ChangePass changePass,Model model){
        try {
            User user =userData.findByEmail(changePass.getEmail());
            if(!changePass.getConfirmPassword().equals(changePass.getPassword()))
                throw new BadRequestException("Password don`t match");
            user.setPassword(passwordEncoder.encode(changePass.getPassword()));
            userData.save(user);
            return "redirect:/";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "account/changePassword/changePassword";
        }
    }

}
