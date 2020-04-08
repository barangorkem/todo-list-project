package com.example.todolistapp.mapper;

import com.example.todolistapp.dto.TodoListItemDto.GetTodoListItemsDto;
import com.example.todolistapp.dto.TodoListItemDto.PostTodoListItemDto;
import com.example.todolistapp.entities.TodoListItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoListItemMapper {
    TodoListItem todoListItemDtoToTodoListItem(PostTodoListItemDto postTodoListItemDto);

    //getTodoListItems
    List<GetTodoListItemsDto> todoListItemToGetTodoListItemsDto(List<TodoListItem> todoListItem);
    @Mapping(source = "todoList", target = "todoList")
    @Mapping(source = "createdAt",target = "createdAt")
    GetTodoListItemsDto todoListItemToGetTodoListItemsDto(TodoListItem todoListItem);


}
