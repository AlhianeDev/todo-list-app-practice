package com.todo_list_app.global.advice;

import com.todo_list_app.global.exceptions.ConflictException;

import com.todo_list_app.global.exceptions.InvalidDataException;
import com.todo_list_app.global.exceptions.NotFoundException;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgs(MethodArgumentNotValidException ex) {

        Map<String, String> errorsMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {

            errorsMap.put(error.getField(), error.getDefaultMessage());

        });

        return errorsMap;

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConflictException.class)
    public Map<String, String> handleConflictException(ConflictException ex) {

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("error_message", ex.getMessage());

        return errorMap;

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> handleNotFoundException(NotFoundException ex) {

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("error_message", ex.getMessage());

        return errorMap;

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidDataException.class)
    public Map<String, String> handleInvalidDataException(InvalidDataException ex) {

        Map<String, String> errorMap = new HashMap<>();

        if (ex.getMessage().equals("email")) {

            errorMap.put("email", "Invalid Email!");

        } else {

            errorMap.put("password", "Invalid Password!");

        }

        return errorMap;

    }

}
