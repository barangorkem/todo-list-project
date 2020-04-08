package com.example.todolistapp.services;

import com.example.todolistapp.dto.TodoListItemDto.UpdateTodoListItemDto;
import com.example.todolistapp.entities.TodoList;
import com.example.todolistapp.entities.TodoListItem;
import com.example.todolistapp.repository.TodoListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TodoListItemServices {

    @Autowired
    TodoListItemRepository todoListItemRepository;

    public void addTodoListItem(TodoListItem todoListItem, TodoList todoList) {
        todoListItem.setTodoList(todoList);
        todoListItem.setStatus(false);
        todoListItem.setActive(true);
        todoListItemRepository.save(todoListItem);
    }

    public void updateTodoListItemStatus(TodoListItem todoListItem,Boolean status) {
        todoListItem.setStatus(status);
        todoListItemRepository.save(todoListItem);
    }
    public void updateTodoListItem(TodoListItem todoListItem, UpdateTodoListItemDto updateTodoListDto) {
        todoListItem.setDeadLine(updateTodoListDto.getDeadLine());
        todoListItem.setName(updateTodoListDto.getName());
        todoListItem.setDescription(updateTodoListDto.getDescription());
        todoListItem.setRelationshipItemId(updateTodoListDto.getRelationshipItemId());
        todoListItemRepository.save(todoListItem);
    }
    public List<TodoListItem> getTodoListItemsByTodoListId(Long todoListId) {
        List<TodoListItem> todoListItem = todoListItemRepository.findAllByTodoListIdAndActive(todoListId,true);
        return todoListItem;
    }
    public TodoListItem getTodoListItemsByTodoListItemId(Long todoListItemId) {
        TodoListItem todoListItem = todoListItemRepository.findByIdAndActive(todoListItemId,true);
        return todoListItem;
    }

    public void deleteTodoListItem(TodoListItem todoListItem) {
        todoListItem.setActive(false);
        todoListItemRepository.save(todoListItem);
    }

    public List<TodoListItem> getTodoListItemByStatus(Long todoListId,Boolean status){
        List<TodoListItem> todoListItem = todoListItemRepository.findAllByTodoListIdAndStatusAndActive(todoListId,status,true);
        return todoListItem;
    }
    public List<TodoListItem> getTodoListItemByDeadLine(Long todoListId){
        Date date = new Date();
        List<TodoListItem> todoListItem = todoListItemRepository.findAllByDeadLineFilter(todoListId,date,true);
        return todoListItem;
    }
}
