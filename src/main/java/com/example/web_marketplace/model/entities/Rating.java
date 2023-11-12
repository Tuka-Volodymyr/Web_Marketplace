package com.example.web_marketplace.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Map;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRating;
    private Long idSeller;
    private int lastRating;
    @ElementCollection
    private Map<Long,Integer> rating=new TreeMap<>();
    public int averageRating(){
        if(rating==null)return 0;
        int suma=rating.values().stream().mapToInt(Integer::intValue).sum();
        for(Integer integer:rating.values()){
            System.out.println(integer);
        }

        return suma/rating.size();
    }
}
