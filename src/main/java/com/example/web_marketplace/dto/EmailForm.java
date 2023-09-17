package com.example.web_marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailForm {
    @NotBlank(message = "Email should not be blank!")
    @Email(message = "It isn`t email format!")
    private String email;
}
