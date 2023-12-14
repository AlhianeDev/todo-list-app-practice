package com.todo_list_app.global.dto;

import com.todo_list_app.global.entities.Todo;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

    private Integer id;

    @NotBlank(message = "Todo title can't be blank!")
    @NotNull(message = "Todo title can't be null!")
    @Size(max = 255, message = "Todo title length must be less than or equal to 255!")
    private String todoTitle;

    @NotBlank(message = "Todo description can't be blank!")
    @NotNull(message = "Todo description can't be null!")
    private String todoDesc;

    private Date createdDate = new Date();

    private Boolean isConfirmed = false;

    private Integer userId;

    public static TodoDto mapToDto(Todo todo) {

        return TodoDto.builder()

            .id(todo.getId())

            .todoTitle(todo.getTodoTitle())

            .todoDesc(todo.getTodoDesc())

            .createdDate(todo.getCreatedDate())

            .isConfirmed(todo.getIsConfirmed())

            .userId(todo.getUserId())

        .build();

    }

}
