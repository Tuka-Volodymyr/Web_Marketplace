package com.example.web_marketplace.service;

import com.example.web_marketplace.data.CodeData;
import com.example.web_marketplace.data.GoodsData;
import com.example.web_marketplace.data.RatingData;
import com.example.web_marketplace.data.UserData;
import com.example.web_marketplace.entities.Rating;
import com.example.web_marketplace.forms.ChangePass;
import com.example.web_marketplace.entities.Code;
import com.example.web_marketplace.entities.User;
import com.example.web_marketplace.exceptions.BadRequestException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static com.example.web_marketplace.service.GoodsService.getUserDetails;


@Service
public class UserService {
    private final GoodsData goodsData;
    private final UserData userData;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final CodeData codeData;
    private final RatingData ratingData;
    public UserService(UserData userData, PasswordEncoder passwordEncoder, CodeData codeData, JavaMailSender javaMailSender, GoodsData goodsData, RatingData ratingData) {
        this.passwordEncoder=passwordEncoder;
        this.userData = userData;
        this.codeData=codeData;
        this.javaMailSender = javaMailSender;
        this.goodsData = goodsData;
        this.ratingData = ratingData;
    }
    public void addUser(User user){
        if(userData.isExist(user.getEmail()))throw new BadRequestException(
                "Email has already used!"
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userData.save(user);
    }
    public void infoOfUser(String email, Model model,HttpSession session){
        session.setAttribute("idSeller",userData.findByEmail(email).getIdUser());
        model.addAttribute("user", userData.findByEmail(email));
    }
    public void goodsToAttribute(Model model,String email){
        model.addAttribute("goods",goodsData.findByEmail(email,GoodsService.sort));
    }
    public void sendCode(String email){
        User user= userData.findByEmail(email);
        if(codeData.findCodeByAccountId(user.getIdUser()).isPresent())
            codeData.delete(codeData.findCodeByAccountId(user.getIdUser()).get());
        Code code=new Code(user.getIdUser());
        codeData.save(code);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("Code: "+code.getCode());
        javaMailSender.send(message);
    }
    public void checkCode(String code){
        codeData.findByCode(code);
    }
    public void changePass(ChangePass changePass){
        User user =userData.findByEmail(changePass.getEmail());
        if(!changePass.getConfirmPassword().equals(changePass.getPassword()))
            throw new BadRequestException("Password don`t match");
        user.setPassword(passwordEncoder.encode(changePass.getPassword()));
        userData.save(user);
    }
    public void ratingToAttribute(Model model,String email){
        Optional<Rating> rating=ratingData.findBySeller(userData.findByEmail(email).getIdUser());
        rating.ifPresent(value -> model.addAttribute("averageRating", value.averageRating()));
        if(rating.isEmpty())model.addAttribute("averageRating",0);
    }
    public void evaluate(Rating rating,HttpSession session){
        rating.setIdSeller((Long) session.getAttribute("idSeller"));
        Optional<Rating> sellerRating=ratingData.findBySeller(rating.getIdSeller());
        if(sellerRating.isPresent()){
            sellerRating.get().getRating().put(userData.findByEmail(getUserDetails().getUsername()).getIdUser(),rating.getLastRating());
            ratingData.save(sellerRating.get());
        }else {
            Map<Long, Integer> map=new TreeMap<>();
            map.put(userData.findByEmail(getUserDetails().getUsername()).getIdUser(),rating.getLastRating());
            map.put(userData.findByEmail(getUserDetails().getUsername()).getIdUser(),rating.getLastRating());
            map.put(userData.findByEmail(getUserDetails().getUsername()).getIdUser(),rating.getLastRating());
            rating.setRating(map);
            ratingData.save(rating);
        }
    }
}
