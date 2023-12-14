package com.todo_list_app.global.services;

import com.todo_list_app.global.dto.TodoDto;

import com.todo_list_app.global.entities.Todo;

import com.todo_list_app.global.exceptions.ConflictException;

import com.todo_list_app.global.exceptions.NotFoundException;

import com.todo_list_app.global.repositories.TodoRepo;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.BDDMockito.given;

import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoRepo todoRepo;

    @InjectMocks
    private TodoService todoService;

    @Test
    @DisplayName("JUnit test for createTodo method")
    public void givenTodoObject_whenSaveTodo_thenReturnTodoObject()

        throws ConflictException {

        Todo todo = Todo.builder()

            .id(1)

            .todoTitle("Todo Title")

            .todoDesc("Todo Description")

        .build();

        given(todoRepo.save(todo)).willReturn(todo);

        Todo underTest = todoService.createTodo(TodoDto.mapToDto(todo));

        assertThat(underTest).isNotNull();

        assertThat(underTest.getId()).isEqualTo(1);

    }

    @Test
    @DisplayName("JUnit test for createTodo method which throws exception")
    public void givenExistingTitle_whenSaveTodo_thenThrowsException() {

        Todo todo = Todo.builder()

                .id(1)

                .todoTitle("Todo Title")

                .todoDesc("Todo Description")

                .build();

        given(todoRepo.findByTodoTitle(todo.getTodoTitle()))

            .willReturn(Optional.of(todo));

        assertThrows(

            ConflictException.class,

            () -> { todoService.createTodo(TodoDto.mapToDto(todo)); }

        );

    }

    @Test
    @DisplayName("JUnit test for updateTodo method")
    public void givenTodoObject_whenUpdateTodo_thenReturnTodoObject()

        throws ConflictException,NotFoundException {

        Todo todo = Todo.builder()

                .id(1)

                .todoTitle("Todo Title")

                .todoDesc("Todo Description")

                .build();

        given(todoRepo.save(todo)).willReturn(todo);

        todo.setTodoTitle("Updated Todo Title");

        todo.setTodoDesc("Updated Todo Desc");

        Todo underTest = todoService.updateTodo(TodoDto.mapToDto(todo));

        assertThat(underTest).isNotNull();

        assertThat(underTest.getTodoTitle()).isEqualTo("Updated Todo Title");

        assertThat(underTest.getTodoDesc()).isEqualTo("Updated Todo Desc");

    }

    @Test
    @DisplayName("JUnit test for updateTodo method which throws exception")
    public void givenExistingTitle_whenUpdateTodo_thenThrowsException() {

        Todo todo = Todo.builder()

            .id(1)

            .todoTitle("Todo Title")

            .todoDesc("Todo Description")

        .build();

        given(todoRepo.findByTodoTitle(todo.getTodoTitle()))

            .willReturn(Optional.of(todo));

        assertThrows(

                ConflictException.class,

                () -> { todoService.updateTodo(TodoDto.mapToDto(todo)); }

        );

    }

    @Test
    @DisplayName("JUnit test for readTodos method")
    public void givenTodosList_whenGetAllTodos_thenReturnTodosList() {

        Todo todo1 = Todo.builder()

            .id(1)

            .todoTitle("Todo Title 1")

            .todoDesc("Todo Description 1")

        .build();

        Todo todo2 = Todo.builder()

            .id(2)

            .todoTitle("Todo Title 2")

            .todoDesc("Todo Description 2")

        .build();

        given(todoRepo.findAll()).willReturn(List.of(todo1, todo2));

        List<Todo> todos = todoService.readTodos(1);

        assertThat(todos).isNotEmpty();

        assertThat(todos.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("JUnit test for readTodoById method")
    public void givenTodoId_whenGetTodoById_thenReturnTodoObject()

        throws NotFoundException {

        Todo todo = Todo.builder()

            .id(1)

            .todoTitle("Todo Title")

            .todoDesc("Todo Description")

        .build();

        given(todoRepo.findById(1)).willReturn(Optional.of(todo));

        Todo underTest = todoService.readTodoById(todo.getId(), 1);

        assertThat(underTest).isNotNull();

    }

    @Test
    @DisplayName("JUnit test for readTodoById method with throw exception")
    public void givenNoExistingId_whenGetTodoById_thenThrowsException() {

        given(todoRepo.findById(1)).willReturn(Optional.empty());

        assertThrows(

            NotFoundException.class,

            () -> { todoService.readTodoById(1, 1); }

        );

    }

    @Test
    @DisplayName("JUnit test for readTodoByTitle method")
    public void givenTodoTitle_whenGetTodoByTitle_thenReturnTodoObject()

        throws NotFoundException {

        Todo todo = Todo.builder()

            .id(1)

            .todoTitle("Todo Title")

            .todoDesc("Todo Description")

        .build();

        given(todoRepo.findByTodoTitle("Todo Title")).willReturn(Optional.of(todo));

        Todo underTest = todoService.readTodoByTitle(todo.getTodoTitle(), 1);

        assertThat(underTest).isNotNull();

    }

    @Test
    @DisplayName("JUnit test for readTodoByTitle method with throw exception")
    public void givenNoExistingTitle_whenGetTodoByTitle_thenThrowsException() {

        given(todoRepo.findByTodoTitle("Todo Title")).willReturn(Optional.empty());

        assertThrows(

            NotFoundException.class,

            () -> { todoService.readTodoByTitle("Todo Title", 1); }

        );

    }

    @Test
    @DisplayName("JUnit test for deleteTodoById method with throw exception")
    public void givenNoExistingId_whenDeleteTodoById_thenThrowsException()

        throws NotFoundException {

        Todo todo = Todo.builder()

            .id(1)

            .todoTitle("Todo Title")

            .todoDesc("Todo Description")

        .build();

        given(todoRepo.findById(1)).willReturn(Optional.of(todo));

        willDoNothing().given(todoRepo).deleteById(1);

        todoService.deleteTodoById(1, 1);

        given(todoRepo.findById(1)).willReturn(Optional.empty());

        assertThrows(

            NotFoundException.class,

            () -> { todoService.readTodoById(1, 1); }

        );

    }

}
