package com.todo_list_app.global.repositories;

import com.todo_list_app.global.entities.Todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepo extends JpaRepository<Todo, Integer> {

    Optional<Todo> findByTodoTitle(String todoTitle);

    List<Todo> findByUserId(Integer userId);

    Page<Todo> findByUserId(Pageable pageable, Integer userId);

    Optional<Todo> findByIdAndUserId(Integer id, Integer userId);

    Optional<Todo> findByTodoTitleAndUserId(String todoTitle, Integer userId);

    boolean existsByTodoTitleAndUserId(String todoTitle, Integer UserId);

}
