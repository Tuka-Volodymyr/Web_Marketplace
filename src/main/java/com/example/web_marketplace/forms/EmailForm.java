package com.example.web_marketplace.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailForm {
    @NotBlank(message = "Email should not be blank!")
    @Email(message = "It isn`t email format!")
    private String email;
}
