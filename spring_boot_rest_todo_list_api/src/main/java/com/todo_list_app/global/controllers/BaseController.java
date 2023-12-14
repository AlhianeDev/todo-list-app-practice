package com.todo_list_app.global.controllers;

import com.todo_list_app.global.security.entites.User;

import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {

    public User getCurrentUser() {

        return (User) SecurityContextHolder.getContext()

            .getAuthentication().getPrincipal();

    }

}
