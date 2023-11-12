package com.example.web_marketplace.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Account")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotBlank(message = "Name should not be blank!")
    @Size(min = 4,max = 20, message = "Name length is wrong!")
    private String fullName;

    @NotBlank(message = "Email should not be blank!")
    @Email(message = "It isn`t email format!")
    private String email;

    @NotBlank(message = "Password should not be blank!")
    private String password;

    @NotBlank(message = "Phone should not be blank!")
    @Size(min = 10,max = 13,message = "Phone length is wrong!")
    private String phone;

    public String toString(){
        return "Name: "+getFullName()+
                "\nEmail: "+getEmail()+
                "\nPhone: "+getPhone();
    }


}
