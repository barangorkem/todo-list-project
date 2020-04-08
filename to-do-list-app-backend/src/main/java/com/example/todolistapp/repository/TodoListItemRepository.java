package com.example.todolistapp.repository;

import com.example.todolistapp.entities.TodoListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TodoListItemRepository extends JpaRepository<TodoListItem, Long> {
    TodoListItem findByIdAndActive(Long todoListItemId, Boolean active);
    List<TodoListItem> findAllByTodoListIdAndActive(Long todoListId,Boolean active);
    TodoListItem findAllByIdAndActive(Long todoListItemId,Boolean active);
    List<TodoListItem> findAllByTodoListIdAndStatusAndActive(Long todoListId,Boolean status,Boolean active);
    @Query("select t from TodoListItem t where t.todoList.id=?1 and t.deadLine<?2 and t.active=?3")
    List<TodoListItem> findAllByDeadLineFilter(Long todoListId, Date deadLine, Boolean active);

}
