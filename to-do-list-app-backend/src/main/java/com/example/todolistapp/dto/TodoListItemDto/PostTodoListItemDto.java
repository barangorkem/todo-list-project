package com.example.todolistapp.dto.TodoListItemDto;

import com.example.todolistapp.entities.TodoList;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class PostTodoListItemDto {

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
