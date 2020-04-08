package com.example.todolistapp.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "TodoList")
@Data
public class TodoList extends BaseEntity {
    @Column(name = "Name")
    @NotBlank(message = "İsim alanı boş geçilemez.")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @Column(name = "Active")
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "todoList")
    private List<TodoListItem> todoListItems;

}
