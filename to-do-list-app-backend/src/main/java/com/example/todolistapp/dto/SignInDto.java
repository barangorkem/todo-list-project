package com.example.todolistapp.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class SignInDto {
    @NotBlank(message = "Kullanıcı adı boş geçilemez.")
    private String username;

    @NotBlank(message = "Password alanı boş geçilemez.")
    private String password;
}
