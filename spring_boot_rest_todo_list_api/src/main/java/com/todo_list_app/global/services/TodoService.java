package com.todo_list_app.global.services;

import com.todo_list_app.global.dto.TodoDto;
import com.todo_list_app.global.entities.Todo;
import com.todo_list_app.global.exceptions.ConflictException;
import com.todo_list_app.global.exceptions.NotFoundException;
import com.todo_list_app.global.repositories.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepo todoRepo;

    public Todo createTodo(TodoDto todoDto) throws ConflictException {

        if (todoRepo.existsByTodoTitleAndUserId(

            todoDto.getTodoTitle(), todoDto.getUserId()

        )) {

            throw new ConflictException("Todo is already exists!");

        }

        return todoRepo.save(Todo.mapToEntity(todoDto));

    }

    public Todo updateTodo(TodoDto todoDto)

        throws ConflictException, NotFoundException {

        if (readTodoById(todoDto.getId(), todoDto.getUserId()) == null) {

            throw new NotFoundException(

                "No todo with ID " + todoDto.getId() + " found!"

            );

        } else if (todoRepo.existsByTodoTitleAndUserId(

            todoDto.getTodoTitle(), todoDto.getUserId()

        )) {

            throw new ConflictException("Todo is already exists!");

        }

        return todoRepo.save(Todo.mapToEntity(todoDto));

    }

    public Page<Todo> getTodoPagination(

        Integer pageNumber, Integer pageSize, String sort, Integer userId

    ) {

        // Pageable is an Abstract interface for pagination information.

        // PageRequest is a class which is an implementation of the Pageable interface.

        Pageable pageable = null;

        if (sort != null) {

            // with sorting

            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sort);

        } else {

            // without sorting

            pageable = PageRequest.of(pageNumber, pageSize);

        }

        // return todoRepo.findAll(pageable);

        return todoRepo.findByUserId(pageable, userId);

    }

    public List<Todo> readTodos(Integer userId) {

        return todoRepo.findByUserId(userId);

    }

    public Todo readTodoById(Integer id, Integer userId) throws NotFoundException {

        Optional<Todo> todo = todoRepo.findByIdAndUserId(id, userId);

        if (todo.isPresent()) {

            return todo.get();

        } else {

            throw new NotFoundException("No todo with ID " + id + " found!");

        }

    }

    public Todo readTodoByTitle(String todoTitle, Integer userId)

        throws NotFoundException {

        Optional<Todo> todo = todoRepo.findByTodoTitleAndUserId(todoTitle, userId);

        if (todo.isPresent()) {

            return todo.get();

        } else {

            throw new NotFoundException("No todo with title " + todoTitle + " found!");

        }

    }

    public void deleteTodoById(Integer id, Integer userId)

        throws NotFoundException {

        if (readTodoById(id, userId) != null) {

            todoRepo.deleteById(id);

        } else {

            throw new NotFoundException("No todo with ID " + id + " found!");

        }

    }

    public void setIsConfirmed(Integer id, Integer userId)

        throws NotFoundException {

        Todo todo = readTodoById(id, userId);

        boolean flag = todo.getIsConfirmed();

        todo.setIsConfirmed(!flag);

        todoRepo.save(todo);

    }

}
