package com.example.web_marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePass {
    @NotBlank(message = "Email should not be blank!")
    @Email(message = "It isn`t email format!")
    private String email;
    @NotBlank(message = "Password should not be blank!")
    private String password;
    @NotBlank(message = "Confirm password should not be blank!")
    private String confirmPassword;
}
