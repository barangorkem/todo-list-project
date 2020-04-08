package com.example.todolistapp.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "User")
@Data
public class User extends BaseEntity {

    @NotBlank(message = "Email alanı boş geçilemez.")
    @Column(name = "Email",nullable = false)
    @Email(message = "Email formatında olmalıdır.")
    private String email;

    @NotBlank(message = "Kullanıcı adı boş geçilemez.")
    @Column(name = "Username",nullable = false)
    private String username;

    @Column(name = "Active")
    private Boolean active;

    @NotBlank(message = "Password alanı boş geçilemez.")
    @Column(name = "Password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<TodoList> todoLists;

}
