package com.todo_list_app.global.security.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "Email can't be blank or null!")
    private String email;

    @NotBlank(message = "Password can't be blank or null!")
    private String password;

}
