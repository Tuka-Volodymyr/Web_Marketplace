package com.example.web_marketplace.service;

import com.example.web_marketplace.repository.impl.CodeRepositoryImplement;
import com.example.web_marketplace.repository.impl.GoodsRepositoryImplement;
import com.example.web_marketplace.repository.impl.RatingRepositoryImplement;
import com.example.web_marketplace.repository.impl.UserRepositoryImplement;
import com.example.web_marketplace.model.entities.Rating;
import com.example.web_marketplace.model.dto.ChangePassDto;
import com.example.web_marketplace.model.entities.Code;
import com.example.web_marketplace.model.entities.User;
import com.example.web_marketplace.exceptions.BadRequestException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static com.example.web_marketplace.service.GoodsService.getUserDetails;


@Service
@RequiredArgsConstructor
public class UserService {
    private final GoodsRepositoryImplement goodsRepositoryImplement;
    private final UserRepositoryImplement userRepositoryImplement;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final CodeRepositoryImplement codeRepositoryImplement;
    private final RatingRepositoryImplement ratingRepositoryImplement;

    public void addUser(User user){
        if(userRepositoryImplement.isExist(user.getEmail()))throw new BadRequestException(
                "Email has already used!"
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepositoryImplement.save(user);
    }
    public void infoOfUser(String email, Model model,HttpSession session){
        session.setAttribute("idSeller", userRepositoryImplement.findByEmail(email).getIdUser());
        model.addAttribute("user", userRepositoryImplement.findByEmail(email));
    }
    public void goodsToAttribute(Model model,String email){
        model.addAttribute("goods", goodsRepositoryImplement.findByEmail(email,GoodsService.sort));
    }
    public void sendCode(String email){
        User user= userRepositoryImplement.findByEmail(email);
        if(codeRepositoryImplement.findCodeByAccountId(user.getIdUser()).isPresent())
            codeRepositoryImplement.delete(codeRepositoryImplement.findCodeByAccountId(user.getIdUser()).get());
        Code code=new Code(user.getIdUser());
        codeRepositoryImplement.save(code);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("Code: "+code.getCode());
        javaMailSender.send(message);
    }
    public void checkCode(String code){
        codeRepositoryImplement.findByCode(code);
    }
    public void changePass(ChangePassDto changePassDto){
        User user = userRepositoryImplement.findByEmail(changePassDto.getEmail());
        if(!changePassDto.getConfirmPassword().equals(changePassDto.getPassword()))
            throw new BadRequestException("Password don`t match");
        user.setPassword(passwordEncoder.encode(changePassDto.getPassword()));
        userRepositoryImplement.save(user);
    }
    public void ratingToAttribute(Model model,String email){
        Optional<Rating> rating= ratingRepositoryImplement.findBySeller(userRepositoryImplement.findByEmail(email).getIdUser());
        rating.ifPresent(value -> model.addAttribute("averageRating", value.averageRating()));
        if(rating.isEmpty())model.addAttribute("averageRating",0);
    }
    public void evaluate(Rating rating,HttpSession session,Model model){
        rating.setIdSeller((Long) session.getAttribute("idSeller"));
        Optional<Rating> sellerRating= ratingRepositoryImplement.findBySeller(rating.getIdSeller());
        if(sellerRating.isPresent()){
            sellerRating.get().getRating().put(userRepositoryImplement.findByEmail(getUserDetails().getUsername()).getIdUser(),rating.getLastRating());
            ratingRepositoryImplement.save(sellerRating.get());
        }else {
            Map<Long, Integer> map=new TreeMap<>();
            map.put(userRepositoryImplement.findByEmail(getUserDetails().getUsername()).getIdUser(),rating.getLastRating());

            rating.setRating(map);
            ratingRepositoryImplement.save(rating);
        }
        User user= userRepositoryImplement.findById(rating.getIdSeller());
        infoOfUser(user.getEmail(),model,session);
        goodsToAttribute(model, user.getEmail());
        ratingToAttribute(model,user.getEmail());
    }

}
