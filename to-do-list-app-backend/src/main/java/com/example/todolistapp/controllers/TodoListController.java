package com.example.todolistapp.controllers;

import com.example.todolistapp.dto.TodoListDto.GetTodoListDto;
import com.example.todolistapp.dto.TodoListDto.GetTodoListsDto;
import com.example.todolistapp.dto.TodoListDto.PostTodoListDto;
import com.example.todolistapp.entities.TodoList;
import com.example.todolistapp.entities.TodoListItem;
import com.example.todolistapp.entities.User;
import com.example.todolistapp.mapper.TodoListMapper;
import com.example.todolistapp.response.BaseResponse;
import com.example.todolistapp.services.TodoListServices;
import com.example.todolistapp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/todolist")
public class TodoListController {

    @Autowired
    TodoListServices todoListServices;

    @Autowired
    TodoListMapper todoListMapper;

    @Autowired
    UserServices userServices;


    @RequestMapping(value = "/postTodoList", method = RequestMethod.POST)
    public ResponseEntity<?> postTodoList(@Valid @RequestBody PostTodoListDto postTodoListDto, BindingResult bindingResult, Principal principal) throws Exception {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(new BaseResponse<List<FieldError>>("Verilerde hata var.", bindingResult.getFieldErrors()));
            }
            TodoList todoList = todoListMapper.todoListDtoToTodoList(postTodoListDto);
            User user = userServices.getUserByUsername(principal.getName());
            todoListServices.addTodoList(todoList,user);
            return ResponseEntity.ok().body(new BaseResponse<>("TodoList ekleme işlemi başarılıdır.", null));
        } catch (Exception e) {
            throw new Exception("Bir hata oluştu", e);
        }
    }

    @RequestMapping(value = "/getTodoLists", method = RequestMethod.GET)
    public ResponseEntity<?> GetTodoLists(Principal principal) throws Exception {
        try {
            User user = userServices.getUserByUsername(principal.getName());

            List<TodoList> todoLists = todoListServices.getTodoLists(user.getId());

            List<GetTodoListsDto> listToGetTodoListsDto = todoListMapper.todoListToGetTodoListsDto(todoLists);

            return ResponseEntity.ok().body(new BaseResponse<>("", listToGetTodoListsDto));

        } catch (Exception e) {
            throw new Exception("Bir hata oluştu", e);
        }
    }

    @RequestMapping(value = "/deleteTodoList/{todoListId}",method = RequestMethod.DELETE)

    public ResponseEntity<?> DeleteTodoList(@PathVariable(name = "todoListId") Long todoListId) throws Exception {
        try {

            TodoList todoList = todoListServices.getTodoList(todoListId);

            todoListServices.deleteTodoList(todoList);

            return ResponseEntity.ok().body(new BaseResponse<>("Silme işlemi başarılıdır.", null));

        } catch (Exception e) {
            throw new Exception("Bir hata oluştu", e);
        }
    }
    @RequestMapping(value = "/getTodoList/{todoListId}",method = RequestMethod.GET)

    public ResponseEntity<?> GetTodoList(@PathVariable(name = "todoListId") Long todoListId) throws Exception {
        try {

            TodoList todoList = todoListServices.getTodoList(todoListId);

            GetTodoListDto todoListDto = todoListMapper.todoListToGetTodoListDto(todoList);

            return ResponseEntity.ok().body(new BaseResponse<>("", todoListDto));

        } catch (Exception e) {
            throw new Exception("Bir hata oluştu", e);
        }
    }
}
