package com.example.todolistapp.services;

import com.example.todolistapp.entities.TodoList;
import com.example.todolistapp.entities.User;
import com.example.todolistapp.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListServices {

    @Autowired
    TodoListRepository todoListRepository;

    @Autowired
    UserServices userServices;

    public void addTodoList(TodoList todoList,User user) {
        todoList.setUser(user);
        todoList.setActive(true);
        todoListRepository.save(todoList);
    }


    public List<TodoList>  getTodoLists(Long userId) {
        return todoListRepository.findAllByUserIdAndActive(userId,true);
    }

    public TodoList getTodoList(Long todoListId) {
        return todoListRepository.findByIdAndActive(todoListId,true);
    }

    public void deleteTodoList(TodoList todoList) {
        todoList.setActive(false);
        todoListRepository.save(todoList);
    }
}
