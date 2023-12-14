package com.todo_list_app.global.controllers;

import com.todo_list_app.global.dto.TodoDto;

import com.todo_list_app.global.entities.Todo;

import com.todo_list_app.global.exceptions.ConflictException;

import com.todo_list_app.global.exceptions.NotFoundException;

import com.todo_list_app.global.services.TodoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/v1/todos")
public class TodoController extends BaseController {

    @Autowired
    private TodoService todoService;

    @CrossOrigin("http://127.0.0.1:5500")
    @PostMapping("")
    public ResponseEntity<Todo> createTodo(@RequestBody @Valid TodoDto todoDto)

        throws ConflictException {

        System.out.println("User Id: " + getCurrentUser().getId());

        todoDto.setUserId(getCurrentUser().getId());

        return new ResponseEntity<>(

            todoService.createTodo(todoDto), HttpStatus.CREATED

        );

    }

    @PutMapping("")
    public ResponseEntity<Todo> updateTodo(@RequestBody @Valid TodoDto todoDto)

        throws ConflictException, NotFoundException {

        todoDto.setUserId(getCurrentUser().getId());

        return new ResponseEntity<>(

            todoService.updateTodo(todoDto), HttpStatus.ACCEPTED

        );

    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<List<Todo>> getTodoPagination(

        @PathVariable Integer pageNumber, @PathVariable Integer pageSize

    ) {

        return new ResponseEntity<>(

           todoService.getTodoPagination(

               pageNumber, pageSize, null, getCurrentUser().getId()

           ).getContent(),

            HttpStatus.OK

        );

    }

    @GetMapping("/{pageNumber}/{pageSize}/{sort}")
    public ResponseEntity<List<Todo>> getTodoPagination(

        @PathVariable Integer pageNumber,

        @PathVariable Integer pageSize,

        @PathVariable String sort

    ) {

        return new ResponseEntity<>(

            todoService.getTodoPagination(

                pageNumber, pageSize, sort, getCurrentUser().getId()

            ).getContent(),

            HttpStatus.OK

        );

    }

    @GetMapping("")
    public ResponseEntity<List<Todo>> readTodos() {

        return new ResponseEntity<>(

            todoService.readTodos(getCurrentUser().getId()), HttpStatus.OK

        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> readTodoById(@PathVariable Integer id)

        throws NotFoundException {

        return new ResponseEntity<>(

            todoService.readTodoById(id, getCurrentUser().getId()),

            HttpStatus.OK

        );

    }

    @GetMapping("/search/{todoTitle}")
    public ResponseEntity<Todo> readTodoByTitle(@PathVariable String todoTitle)

        throws NotFoundException {

        return new ResponseEntity<>(

            todoService.readTodoByTitle(todoTitle, getCurrentUser().getId()),

            HttpStatus.OK

        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Integer id)

        throws NotFoundException {

        todoService.deleteTodoById(id, getCurrentUser().getId());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/confirmation/{id}")
    public ResponseEntity<Void> setIsConfirmed(@PathVariable Integer id)

        throws NotFoundException {

        todoService.setIsConfirmed(id, getCurrentUser().getId());

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
