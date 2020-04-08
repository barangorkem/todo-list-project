package com.example.todolistapp.repository;

import com.example.todolistapp.entities.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoList,Long> {
    List<TodoList> findAllByUserIdAndActive(Long userId,Boolean active);

    TodoList findByIdAndActive(Long todoListId,Boolean active);

}
