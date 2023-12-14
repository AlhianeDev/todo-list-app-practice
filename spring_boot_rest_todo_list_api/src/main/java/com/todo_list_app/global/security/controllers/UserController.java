package com.todo_list_app.global.security.controllers;

import com.todo_list_app.global.controllers.BaseController;
import com.todo_list_app.global.exceptions.NotFoundException;
import com.todo_list_app.global.security.entites.User;

import com.todo_list_app.global.security.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends BaseController {

    private final AuthenticationService authenticationService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {

        return ResponseEntity.ok(authenticationService.findUserById(id));

    }

    @CrossOrigin("http://127.0.0.1:5500")
    @PatchMapping("/update-firstName")
    public ResponseEntity<Integer> updateFirstName(@RequestHeader String firstName) {

        return new ResponseEntity<>(

            authenticationService.updateFirstName(

                firstName, getCurrentUser().getId()

            ), HttpStatus.OK

        );

    }

    @CrossOrigin("http://127.0.0.1:5500")
    @PatchMapping("/update-lastName")
    public ResponseEntity<Integer> updateLastName(@RequestHeader String lastName) {

        return new ResponseEntity<>(

            authenticationService.updateLastName(lastName, getCurrentUser().getId()),

            HttpStatus.OK

        );

    }

    @CrossOrigin("http://127.0.0.1:5500")
    @PatchMapping("/update-email")
    public ResponseEntity<Integer> updateEmail(

        @RequestHeader String password, @RequestHeader String newEmail

    ) throws NotFoundException {

        return new ResponseEntity<>(

            authenticationService.updateEmail(

                getCurrentUser().getEmail(), password,

                newEmail, getCurrentUser().getId()

            ), HttpStatus.OK

        );

    }

    @CrossOrigin("http://127.0.0.1:5500")
    @PatchMapping("/update-password")
    public ResponseEntity<Integer> updatePassword(

        @RequestHeader String oldPassword, @RequestHeader String newPassword

    ) throws NotFoundException {

        return new ResponseEntity<>(

            authenticationService.updatePassword(

                getCurrentUser().getEmail(), oldPassword,

                newPassword, getCurrentUser().getId()

            ),

            HttpStatus.OK

        );

    }

    @CrossOrigin("http://127.0.0.1:5500")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {

        authenticationService.deleteAccount(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
