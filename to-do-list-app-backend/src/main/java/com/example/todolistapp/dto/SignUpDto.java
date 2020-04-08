package com.example.todolistapp.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignUpDto {
    @NotBlank(message = "Email alanı boş geçilemez.")
    @Email(message = "Email formatında olmalıdır.")
    private String email;

    @NotBlank(message = "Kullanıcı adı boş geçilemez.")
    private String username;

    private Boolean active;

    @NotBlank(message = "Password alanı boş geçilemez.")
    private String password;
}
