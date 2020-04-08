package com.example.todolistapp.response;

import lombok.Data;

@Data
public class BaseResponse<T> {

    private T payload;

    private String message;

    public BaseResponse(String message,T payload) {
        this.payload = payload;
        this.message=message;

    }
}
