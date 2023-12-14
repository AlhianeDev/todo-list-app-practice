package com.todo_list_app.global.repositories;

import com.todo_list_app.global.entities.Todo;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TodoRepoTest {

    @Autowired
    private TodoRepo todoRepo;

    @AfterEach
    void tearDown() {

        todoRepo.deleteAll();

    }

    @Test
    @DisplayName("JUnit Test for save todo operation")
    public void itShouldBeReturnSavedTodoWhenItSavedOn() {

        Todo todo = todoRepo.save(

            Todo.builder()

                .todoTitle("Todo Title")

                .todoDesc("Todo Description")

            .build()

        );

        assertThat(todo).isNotNull();

        assertThat(todo.getId()).isEqualTo(1);

    }

    @Test
    @DisplayName("JUnit Test for update todo operation")
    public void itShouldBeReturnUpdatedTodoWhenItUpdatedOn() {

        Todo todo = todoRepo.save(

            Todo.builder()

                .id(1)

                .todoTitle("Updated Todo")

                .todoDesc("Todo Description")

            .build()

        );

        assertThat(todo).isNotNull();

        assertThat(todo.getId()).isEqualTo(1);

        assertThat(todo.getTodoTitle()).isEqualTo("Updated Todo");

    }

    @Test
    @DisplayName("JUnit Test for get todos list operation")
    public void itShouldBeReturnTodosList() {

        todoRepo.save(

            Todo.builder()

                .todoTitle("Todo Number One")

                .todoDesc("Todo Description For Todo Number One")

            .build()

        );

        todoRepo.save(

            Todo.builder()

                .todoTitle("Todo Number Two")

                .todoDesc("Todo Description For Todo Number Two")

            .build()

        );

        List<Todo> todos = todoRepo.findAll();

        assertThat(todos).isNotEmpty();

        assertThat(todos.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("JUnit Test for get todo by id operation")
    public void itShouldBeReturnTodoById() {

        todoRepo.save(

            Todo.builder()

                .todoTitle("Todo Title")

                .todoDesc("Todo Description")

            .build()

        );

        Todo todo = todoRepo.findById(1).get();

        assertThat(todo).isNotNull();

    }

    @Test
    @DisplayName("JUnit Test for get todo by title operation")
    public void itShouldBeReturnTodoByTitle() {

        todoRepo.save(

            Todo.builder()

                .todoTitle("Todo Title")

                .todoDesc("Todo Description")

            .build()

        );

        Todo todo = todoRepo.findByTodoTitle("Todo Title").orElseThrow();

        assertThat(todo).isNotNull();

    }

    @Test
    @DisplayName("JUnit Test for delete todo by id operation")
    public void itShouldBeDeleteTodoById() {

        todoRepo.save(

            Todo.builder()

                .todoTitle("Todo Title")

                .todoDesc("Todo Description")

            .build()

        );

        todoRepo.deleteById(1);

        Optional<Todo> todo = todoRepo.findById(1);

        assertThat(todo).isEmpty();

    }

}
