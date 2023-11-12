package com.example.web_marketplace.model.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.NumberFormat;


import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGood;

    @NotBlank(message = "Name should not be blank!")
    private String name;

    @NotBlank(message = "Description should not be blank!")
    private String description;

    @NotBlank(message = "Category should not be blank!")
    private String category;

    @NumberFormat
    private long price;

//    private boolean goodIsAvailable;

    private String photoOfGood;
    @Column(length = 10000000)
    private byte[] bytePhoto;

    private LocalDate localDate;
    private String userEmail;

}
