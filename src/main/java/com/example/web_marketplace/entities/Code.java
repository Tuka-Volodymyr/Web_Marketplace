package com.example.web_marketplace.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Long userId;
    public Code(long userId){
        this.userId=userId;
        code=generateCod();
    }

    public String generateCod() {
        StringBuilder stringBuilderCod=new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            stringBuilderCod.append(rand.nextInt(10));
        }
        return stringBuilderCod.toString();
    }
}
