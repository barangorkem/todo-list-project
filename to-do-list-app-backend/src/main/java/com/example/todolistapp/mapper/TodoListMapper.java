package com.example.todolistapp.mapper;


import com.example.todolistapp.dto.TodoListDto.GetTodoListDto;
import com.example.todolistapp.dto.TodoListDto.GetTodoListsDto;
import com.example.todolistapp.dto.TodoListDto.PostTodoListDto;
import com.example.todolistapp.entities.TodoList;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoListMapper {
    TodoList todoListDtoToTodoList(PostTodoListDto postTodoListDto);

    //getTodoLists
    List<GetTodoListsDto> todoListToGetTodoListsDto(List<TodoList> todoList);
    GetTodoListsDto todoListToGetTodoListsDto(TodoList todoList);

    //getTodoList
    List<GetTodoListDto> todoListToGetTodoListDto(List<TodoList> todoList);
    GetTodoListDto todoListToGetTodoListDto(TodoList todoList);



}
