package com.example.todolistapp.mapper;


import com.example.todolistapp.dto.SignUpDto;
import com.example.todolistapp.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


/*
    @Mapping(source = "numberOfSeats", target = "seatCount")
*/
    User signUpDtoToUserDto(SignUpDto signUpDto);
}
