package com.todo_list_app.global.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.todo_list_app.global.dto.TodoDto;

import com.todo_list_app.global.entities.Todo;

import com.todo_list_app.global.services.TodoService;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest  {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @MockBean
    private TodoService todoService;

    private Todo todo;

    @BeforeEach
    public void setUp() throws Exception {

        todo = Todo.builder()

            .id(1)

            .todoTitle("Todo Title")

            .todoDesc("Todo Description")

        .build();

    }

    public static String asJsonString(final Object obj) {

        try {

            return new ObjectMapper().writeValueAsString(obj);

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    @Test
    @DisplayName("JUnit test for post todo request")
    public void canCreateTodo() throws Exception {

        when(todoService.createTodo(TodoDto.mapToDto(todo))).thenReturn(todo);

        mvc.perform(

            post("/api/v1/todos")

            .content(asJsonString(todo))

            .contentType(MediaType.APPLICATION_JSON)

        )

        .andExpect(status().isCreated())

        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

    }

    @Test
    @DisplayName("JUnit test for put todo request")
    public void canUpdateTodoById() throws Exception {

        todo.setTodoTitle("Updated Todo");

        when(todoService.updateTodo(TodoDto.mapToDto(todo))).thenReturn(todo);

        mvc.perform(

            put("/api/v1/todos")

            .content(asJsonString(todo))

            .contentType(MediaType.APPLICATION_JSON)

        )

        .andExpect(status().isAccepted())

        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())

        .andExpect(MockMvcResultMatchers.jsonPath("$.todoTitle")

            .value("Updated Todo"));

    }

    @Test
    @DisplayName("JUnit test for get todos request")
    public void canGetTodos() throws Exception {

        when(todoService.readTodos(1).thenReturn(List.of(todo));

        mvc.perform(

            get("/api/v1/todos")

            .contentType(MediaType.APPLICATION_JSON)

        )

        .andExpect(status().isOk())

        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))

        .andExpect(MockMvcResultMatchers.jsonPath("$[0].todoTitle")

                .value("Todo Title"));

    }

    @Test
    @DisplayName("JUnit test for get todo by id request")
    public void canGetTodoById() throws Exception {

        when(todoService.readTodoById(1, 1)).thenReturn(todo);

        mvc.perform(

            get("/api/v1/todos/1")

            .contentType(MediaType.APPLICATION_JSON)

        )

        .andExpect(status().isOk())

        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

    }

    @Test
    @DisplayName("JUnit test for get todo by title request")
    public void canGetTodoByTitle() throws Exception {

        when(todoService.readTodoByTitle(

            "Todo Title", 1

        )).thenReturn(todo);

        mvc.perform(

            get("/api/v1/todos/search/Todo Title")

            .contentType(MediaType.APPLICATION_JSON)

        )

        .andExpect(status().isOk())

        .andExpect(MockMvcResultMatchers.jsonPath("$.todoTitle")

            .value("Todo Title"));

    }

    @Test
    @DisplayName("JUnit test for delete todo by id request")
    public void canDeleteTodoById() throws Exception {

        when(todoService.readTodoById(1, 1)).thenReturn(todo);

        mvc.perform(delete("/api/v1/todos/1"))

            .andExpect(status().isOk());

    }

}
