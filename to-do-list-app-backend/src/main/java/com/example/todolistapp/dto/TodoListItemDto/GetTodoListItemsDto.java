package com.example.todolistapp.dto.TodoListItemDto;

import com.example.todolistapp.dto.TodoListDto.GetTodoListDto;
import com.example.todolistapp.entities.TodoList;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class GetTodoListItemsDto {

    private Long id;

    private String name;

    private String description;

    private Date deadLine;

    private Boolean status;

    private Date createdAt;

    private Boolean active;

    private Long relationshipItemId;

    private GetTodoListDto todoList;
}
