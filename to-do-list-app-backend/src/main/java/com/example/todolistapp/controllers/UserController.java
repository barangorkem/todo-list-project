package com.example.todolistapp.controllers;

import com.example.todolistapp.dto.SignInDto;
import com.example.todolistapp.dto.SignUpDto;
import com.example.todolistapp.entities.User;
import com.example.todolistapp.mapper.UserMapper;
import com.example.todolistapp.response.BaseResponse;
import com.example.todolistapp.services.UserServices;
import com.example.todolistapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.example.todolistapp.repository.UserRepository;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServices userServices;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto signUpDto, BindingResult bindingResult) throws Exception {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(new BaseResponse<List<FieldError>>("Verilerde hata var.", bindingResult.getFieldErrors()));
            }

            User user = userMapper.signUpDtoToUserDto(signUpDto);

            if (userServices.getUserByEmail(user.getEmail()) != null) {
                return ResponseEntity.badRequest().body(new BaseResponse<List<FieldError>>("Bu email'de kullanıcı var.", null));
            }
            if(userServices.getUserByUsername(user.getUsername()) != null) {
                return ResponseEntity.badRequest().body(new BaseResponse<List<FieldError>>("Bu kullanıcı adı daha önceden alınmıştır.", null));
            }
            userServices.userSave(user);
        } catch (Exception e) {
            throw new Exception("Bir hata oluştu", e);
        }
        return ResponseEntity.ok().body(new BaseResponse<String>("Kayıt işlemi başarılıdır.", null));
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInDto signInDto, BindingResult bindingResult) throws Exception {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(new BaseResponse<List<FieldError>>("Verilerde hata var.", bindingResult.getFieldErrors()));
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Yanlış kullanıcı adı veya şifre", e);
        }
        final UserDetails user = userServices.loadUserByUsername(signInDto.getUsername());

        final String jwt = jwtUtil.generateToken(user);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", jwt);
        return ResponseEntity.ok().headers(responseHeaders).body(new BaseResponse<String>("Giriş işlemi başarılıdır", null));
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello";
    }
}
