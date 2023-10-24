package com.example.web_marketplace.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




import org.springframework.format.annotation.NumberFormat;


import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
    @Column(length = 10000000)
    private String photoOfGood;
//    private byte[] photoOfGood;

    private LocalDate localDate;
    private String userEmail;

}
