package com.example.web_marketplace.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePass {

    private String email;
    @NotBlank(message = "Password should not be blank!")
    private String password;
    @NotBlank(message = "Confirm password should not be blank!")
    private String confirmPassword;
}
