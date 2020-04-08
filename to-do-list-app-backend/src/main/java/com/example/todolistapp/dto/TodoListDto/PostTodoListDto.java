package com.example.todolistapp.dto.TodoListDto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostTodoListDto {

    @NotBlank(message = "İsim alanı boş olmamalıdır")
    private String name;
}
