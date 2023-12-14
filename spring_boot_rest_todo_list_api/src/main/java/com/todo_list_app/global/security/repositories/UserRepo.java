package com.todo_list_app.global.security.repositories;

import com.todo_list_app.global.security.entites.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String username);

    Optional<User> findByPassword(String password);

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.firstName = :firstName WHERE user.id = :id")
    Integer updateFirstName(String firstName, Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.lastName = :lastName WHERE user.id = :id")
    Integer updateLastName(String lastName, Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.email = :email WHERE user.id = :id")
    Integer updateEmail(String email, Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.password = :password WHERE user.id = :id")
    Integer updatePassword(String password, Integer id);

}
