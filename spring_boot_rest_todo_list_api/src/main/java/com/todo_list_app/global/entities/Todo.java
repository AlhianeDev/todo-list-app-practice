package com.todo_list_app.global.entities;

import com.todo_list_app.global.dto.TodoDto;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "todo_title")
    private String todoTitle;

    @Column(name = "todo_desc")
    private String todoDesc;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "is_confirmed")
    private Boolean isConfirmed = false;

    @Column(name = "user_id")
    private Integer userId;

    public static Todo mapToEntity(TodoDto todoDto) {

        return Todo.builder()

            .id(todoDto.getId())

            .todoTitle(todoDto.getTodoTitle())

            .todoDesc(todoDto.getTodoDesc())

            .createdDate(todoDto.getCreatedDate())

            .isConfirmed(todoDto.getIsConfirmed())

            .userId(todoDto.getUserId())

        .build();

    }

}
