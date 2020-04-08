package com.example.todolistapp;

import com.example.todolistapp.controllers.UserController;
import com.example.todolistapp.dto.SignInDto;
import com.example.todolistapp.dto.SignUpDto;
import com.example.todolistapp.entities.User;
import com.example.todolistapp.mapper.UserMapper;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)

public class UserTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServices userServices;

    @MockBean
    BCryptPasswordEncoder passwordEncoder;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    UserMapper userMapper;


    @Test
    public void signUpUser_NotUniqueEmail() throws Exception
    {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("asd");
        signUpDto.setPassword("asd");
        signUpDto.setEmail("asd@gmail.com");

        User user = new User();
        user.setUsername("asd");
        user.setEmail("das");
        user.setEmail("qweqweq");
        user.setActive(true);
        Mockito.when(userMapper.signUpDtoToUserDto(signUpDto))
                .thenReturn(user);
        Mockito.when(userServices.getUserByEmail(Mockito.anyString()))
                .thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonValue = objectMapper.writeValueAsString(signUpDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signUp")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.payload").isEmpty())
                .andExpect(jsonPath("$.message",is("Bu email'de kullanıcı var.") ));
    }
    @Test
    public void signUpUser_NotUniqueUsername() throws Exception
    {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("asd");
        signUpDto.setPassword("asd");
        signUpDto.setEmail("asd@gmail.com");

        User user = new User();
        user.setUsername("asd");
        user.setEmail("das");
        user.setEmail("qweqweq");
        user.setActive(true);
        Mockito.when(userMapper.signUpDtoToUserDto(signUpDto))
                .thenReturn(user);
        Mockito.when(userServices.getUserByEmail(Mockito.anyString()))
                .thenReturn(null);
        Mockito.when(userServices.getUserByUsername(Mockito.anyString()))
                .thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonValue = objectMapper.writeValueAsString(signUpDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signUp")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.payload").isEmpty())
                .andExpect(jsonPath("$.message",is("Bu kullanıcı adı daha önceden alınmıştır.") ));
    }
    @Test
    public void signUpUser_Success() throws Exception
    {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("asd");
        signUpDto.setPassword("asd");
        signUpDto.setEmail("asd@gmail.com");

        User user = new User();
        user.setUsername("asd");
        user.setEmail("das");
        user.setEmail("qweqweq");
        user.setActive(true);
        Mockito.when(userMapper.signUpDtoToUserDto(signUpDto))
                .thenReturn(user);
        Mockito.when(userServices.getUserByEmail(Mockito.anyString()))
                .thenReturn(null);
        Mockito.when(userServices.getUserByUsername(Mockito.anyString()))
                .thenReturn(null);

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonValue = objectMapper.writeValueAsString(signUpDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signUp")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.payload").isEmpty())
                .andExpect(jsonPath("$.message",is("Kayıt işlemi başarılıdır.") ));
    }
}
