package com.example.web_marketplace.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UserOrders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;
    private Long idBuyerAccount;
    private String name;
    private String email;
    private String phone;
    //зберігай не goods а фото і назву!!!

//    private List<Goods> service;
    @ElementCollection
    private List<String> nameOfGood;
    private String city;
    private String postOffice;
    private String address;
    private LocalDate date;
    private String delivery;
    private String payment;
    private String comments;
    private long price;


}
