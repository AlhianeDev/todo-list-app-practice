package com.todo_list_app.global.security.controllers;

import com.todo_list_app.global.exceptions.ConflictException;
import com.todo_list_app.global.exceptions.InvalidDataException;
import com.todo_list_app.global.security.requests.LoginRequest;
import com.todo_list_app.global.security.requests.RegisterRequest;
import com.todo_list_app.global.security.response.AuthenticationResponse;
import com.todo_list_app.global.security.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(

        @RequestBody @Valid RegisterRequest request

    ) throws IOException, ConflictException {

        return ResponseEntity.ok(authenticationService.register(request));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(

        @RequestBody @Valid LoginRequest request

    ) throws InvalidDataException {

        return ResponseEntity.ok(authenticationService.login(request));

    }

}
