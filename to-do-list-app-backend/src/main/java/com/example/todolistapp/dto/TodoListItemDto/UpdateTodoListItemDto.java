package com.example.todolistapp.dto.TodoListItemDto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UpdateTodoListItemDto {
    @NotBlank(message = "İsim alanı zorunludur.")
    private String name;

    @NotBlank(message = "Açıklama alanı zorunludur.")
    private String description;

    private Date deadLine;

    private Boolean status;

    private Boolean active;

    private Long relationshipItemId;

    private Long todoId;
}
