package com.example.web_marketplace.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTotalPrice;
    private Long idUser;
    private long suma;
}
