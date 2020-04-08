package com.example.todolistapp;

import com.example.todolistapp.controllers.TodoListController;
import com.example.todolistapp.controllers.UserController;
import com.example.todolistapp.dto.SignInDto;
import com.example.todolistapp.dto.SignUpDto;
import com.example.todolistapp.dto.TodoListDto.GetTodoListDto;
import com.example.todolistapp.dto.TodoListDto.GetTodoListsDto;
import com.example.todolistapp.dto.TodoListDto.PostTodoListDto;
import com.example.todolistapp.entities.TodoList;
import com.example.todolistapp.entities.User;
import com.example.todolistapp.mapper.TodoListMapper;
import com.example.todolistapp.mapper.UserMapper;
import com.example.todolistapp.response.BaseResponse;
import com.example.todolistapp.services.TodoListServices;
import com.example.todolistapp.services.UserServices;
import com.example.todolistapp.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TodoListController.class)

public class TodoListTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServices userServices;

    @MockBean
    TodoListServices todoListServices;

    @MockBean
    TodoListMapper todoListMapper;

    @MockBean
    BCryptPasswordEncoder passwordEncoder;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    UserMapper userMapper;


    @Test
    public void getTodoListsSuccess() throws Exception {

        User user = new User();
        user.setId(Long.valueOf(1));
        user.setUsername("asd");
        user.setEmail("das");
        user.setEmail("qweqweq");
        user.setActive(true);

        GetTodoListsDto getTodoListsDto = new GetTodoListsDto();

        getTodoListsDto.setName("name");
        getTodoListsDto.setId(Long.valueOf(1));

        TodoList todolist = new TodoList();

        todolist.setName("name");
        todolist.setActive(true);

        List<TodoList> todoLists = new ArrayList<>();

        todoLists.add(todolist);

        List<GetTodoListsDto> getTodoListsDto2 = new ArrayList<>();

        getTodoListsDto2.add(getTodoListsDto);

        Mockito.when(userServices.getUserByUsername(Mockito.anyString()))
                .thenReturn(user);
        Mockito.when(todoListServices.getTodoLists(Mockito.anyLong()))
                .thenReturn(todoLists);
        Mockito.when(todoListMapper.todoListToGetTodoListsDto(todoLists))
                .thenReturn(getTodoListsDto2);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");


        mockMvc.perform(MockMvcRequestBuilders.get("/api/todolist/getTodoLists").principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.payload").isNotEmpty())
                .andExpect(jsonPath("$.message").isEmpty());
    }

    @Test
    public void deleteTodoListSuccess() throws Exception {


        TodoList todolist = new TodoList();

        todolist.setName("name");
        todolist.setActive(true);


        Mockito.when(todoListServices.getTodoList(Mockito.anyLong())).thenReturn(todolist);


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todolist/deleteTodoList/2")
                .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.payload").isEmpty())
                .andExpect(jsonPath("$.message", is("Silme işlemi başarılıdır.")));
    }

    @Test
    public void getTodoListSuccess() throws Exception {

        TodoList todoList = new TodoList();
        todoList.setActive(true);
        todoList.setName("name");

        GetTodoListDto getTodoListDto = new GetTodoListDto();
        getTodoListDto.setName("name");
        getTodoListDto.setId(Long.valueOf(1));

        Mockito.when(todoListServices.getTodoList(Mockito.anyLong())).thenReturn(todoList);
        Mockito.when(todoListMapper.todoListToGetTodoListDto(todoList)).thenReturn(getTodoListDto);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/todolist/getTodoList/1")
                .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.payload").isNotEmpty())
                .andExpect(jsonPath("$.message").isEmpty());
    }


    @Test
    public void postTodoListSuccess() throws Exception {


        PostTodoListDto postTodoListDto = new PostTodoListDto();
        postTodoListDto.setName("name");

        User user = new User();
        user.setId(Long.valueOf(1));
        user.setActive(true);
        user.setEmail("email");
        user.setUsername("username");

        TodoList todoList = new TodoList();
        todoList.setId(Long.valueOf(1));
        todoList.setActive(true);
        todoList.setName("name");
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");

        Mockito.when(todoListMapper.todoListDtoToTodoList(postTodoListDto)).thenReturn(todoList);
        Mockito.when(userServices.getUserByUsername(Mockito.anyString())).thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonValue = objectMapper.writeValueAsString(postTodoListDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/todolist/postTodoList").principal(mockPrincipal)
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.payload").isEmpty())
                .andExpect(jsonPath("$.message",is("TodoList ekleme işlemi başarılıdır.")));
    }

}
