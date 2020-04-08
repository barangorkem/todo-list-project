package com.example.todolistapp.dto.TodoListItemDto;

import lombok.Data;

@Data
public class UpdateTodoListItemStatusDto {

    private Boolean status;

    private Long relationshipItemId;
}
