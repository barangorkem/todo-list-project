package com.example.todolistapp.controllers;

import com.example.todolistapp.dto.TodoListItemDto.GetTodoListItemsDto;
import com.example.todolistapp.dto.TodoListItemDto.PostTodoListItemDto;
import com.example.todolistapp.dto.TodoListItemDto.UpdateTodoListItemDto;
import com.example.todolistapp.dto.TodoListItemDto.UpdateTodoListItemStatusDto;
import com.example.todolistapp.entities.TodoList;
import com.example.todolistapp.entities.TodoListItem;
import com.example.todolistapp.mapper.TodoListItemMapper;
import com.example.todolistapp.response.BaseResponse;
import com.example.todolistapp.services.TodoListItemServices;
import com.example.todolistapp.services.TodoListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/todolistitem")
public class TodoListItemController {

    @Autowired
    TodoListItemMapper todoListItemMapper;

    @Autowired
    TodoListItemServices todoListItemServices;

    @Autowired
    TodoListServices todoListServices;

    @RequestMapping(value = "/postTodoListItem", method = RequestMethod.POST)
    public ResponseEntity<?> postTodoList(@Valid @RequestBody PostTodoListItemDto postTodoListItemDto, BindingResult bindingResult) throws Exception {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(new BaseResponse<>("Verilerde hata var.", bindingResult.getFieldErrors()));
            }

            TodoListItem todoListItem = todoListItemMapper.todoListItemDtoToTodoListItem(postTodoListItemDto);
            TodoList todoList = todoListServices.getTodoList(postTodoListItemDto.getTodoId());
            if (todoList == null) {
                return ResponseEntity.badRequest().body(new BaseResponse<>("TodoList bulunamadı", null));
            }
            if (postTodoListItemDto.getRelationshipItemId() != 0 && todoListItemServices.getTodoListItemsByTodoListItemId(postTodoListItemDto.getRelationshipItemId()) == null) {
                return ResponseEntity.badRequest().body(new BaseResponse<>("Item bulunamadı", null));
            }
            todoListItemServices.addTodoListItem(todoListItem, todoList);
            return ResponseEntity.ok().body(new BaseResponse<>("TodoListItem ekleme işlemi başarılıdır.", null));
        } catch (Exception e) {
            throw new Exception("Bir hata oluştu", e);
        }
    }

    @RequestMapping(value = "/getTodoListItems/{todoListId}", method = RequestMethod.GET)
    public ResponseEntity<?> getTodoListItems(@PathVariable(name = "todoListId") Long todoListId) throws Exception {
        try {
            List<TodoListItem> todoListItems = todoListItemServices.getTodoListItemsByTodoListId(todoListId);

            List<GetTodoListItemsDto> listItemToGetTodoListItemsDto = todoListItemMapper.todoListItemToGetTodoListItemsDto(todoListItems);

            return ResponseEntity.ok().body(new BaseResponse<>("", listItemToGetTodoListItemsDto));

        } catch (Exception e) {
            throw new Exception("Bir hata oluştu", e);
        }
    }
    @RequestMapping(value = "/getTodoListItemsFilter/{todoListId}", method = RequestMethod.GET)
    public ResponseEntity<?> getTodoListItemsFilter(@RequestParam(name = "filterId") Integer filterId,@PathVariable(name = "todoListId") Long todoListId) throws Exception {
        List<TodoListItem> todoListItems=null;
        try {
            switch (filterId){
                case 0 :{
                    todoListItems = todoListItemServices.getTodoListItemsByTodoListId(todoListId);
                    break;
                }
                case 1 : {
                    todoListItems = todoListItemServices.getTodoListItemByStatus(todoListId,true);
                    break;
                }
                case 2 : {
                    todoListItems = todoListItemServices.getTodoListItemByStatus(todoListId,false);
                    break;
                }
                case 3 : {
                    todoListItems = todoListItemServices.getTodoListItemByDeadLine(todoListId);
                    break;
                }
            }

            List<GetTodoListItemsDto> listItemToGetTodoListItemsDto = todoListItemMapper.todoListItemToGetTodoListItemsDto(todoListItems);

            return ResponseEntity.ok().body(new BaseResponse<>("", listItemToGetTodoListItemsDto));

        } catch (Exception e) {
            throw new Exception("Bir hata oluştu", e);
        }
    }

    @RequestMapping(value = "/getTodoListItem/{todoListItemId}", method = RequestMethod.GET)
    public ResponseEntity<?> getTodoListItem(@PathVariable(name = "todoListItemId") Long todoListItemId) throws Exception {
        try {
            TodoListItem todoListItem = todoListItemServices.getTodoListItemsByTodoListItemId(todoListItemId);

            GetTodoListItemsDto listItemToGetTodoListItemDto = todoListItemMapper.todoListItemToGetTodoListItemsDto(todoListItem);

            return ResponseEntity.ok().body(new BaseResponse<>("", listItemToGetTodoListItemDto));

        } catch (Exception e) {
            throw new Exception("Bir hata oluştu", e);
        }
    }

    @RequestMapping(value = "/updateItemStatus/{itemId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateItemStatus(@PathVariable(name = "itemId") Long itemId, @RequestBody UpdateTodoListItemStatusDto updateTodoListItemStatusDto) throws Exception {
        try {

            TodoListItem todoListItem = todoListItemServices.getTodoListItemsByTodoListItemId(itemId);

            if (todoListItem == null) {
                return ResponseEntity.badRequest().body(new BaseResponse<>("Item bulunamadı", null));
            }
            if (todoListItem.getRelationshipItemId() != 0) {
                if (todoListItemServices.getTodoListItemsByTodoListItemId(todoListItem.getRelationshipItemId()).getStatus() != true && updateTodoListItemStatusDto.getStatus() == true) {
                    return ResponseEntity.badRequest().body(new BaseResponse<>("Öncelikle bağlantılı olan item'ın bitmesi gereklidir.", null));
                }
            }
            todoListItemServices.updateTodoListItemStatus(todoListItem, updateTodoListItemStatusDto.getStatus());
            return ResponseEntity.ok().body(new BaseResponse<>("Güncelleme işlemi başarılıdır.", null));

        } catch (Exception e) {
            throw new Exception("Bir hata oluştu", e);
        }
    }
    @RequestMapping(value = "/updateTodoList/{itemId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTodoList(@PathVariable(name = "itemId") Long itemId, @RequestBody UpdateTodoListItemDto updateTodoListItemDto) throws Exception {
        try {

            TodoListItem todoListItem = todoListItemServices.getTodoListItemsByTodoListItemId(itemId);

            if (todoListItem == null) {
                return ResponseEntity.badRequest().body(new BaseResponse<>("Item bulunamadı", null));
            }

            todoListItemServices.updateTodoListItem(todoListItem, updateTodoListItemDto);
            return ResponseEntity.ok().body(new BaseResponse<>("Güncelleme işlemi başarılıdır.", null));

        } catch (Exception e) {
            throw new Exception("Bir hata oluştu", e);
        }
    }
    @RequestMapping(value = "/deleteTodoListItem/{todoListItemId}",method = RequestMethod.DELETE)

    public ResponseEntity<?> DeleteTodoList(@PathVariable(name = "todoListItemId") Long todoListItemId) throws Exception {
        try {
            TodoListItem todoListItem = todoListItemServices.getTodoListItemsByTodoListItemId(todoListItemId);
            todoListItemServices.deleteTodoListItem(todoListItem);
            return ResponseEntity.ok().body(new BaseResponse<>("Silme işlemi başarılıdır.", null));

        } catch (Exception e) {
            throw new Exception("Bir hata oluştu", e);
        }
    }

}
