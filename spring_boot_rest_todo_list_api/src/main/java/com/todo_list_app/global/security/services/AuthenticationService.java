package com.todo_list_app.global.security.services;

import com.todo_list_app.global.exceptions.ConflictException;
import com.todo_list_app.global.exceptions.InvalidDataException;
import com.todo_list_app.global.exceptions.NotFoundException;
import com.todo_list_app.global.security.entites.Role;
import com.todo_list_app.global.security.entites.User;
import com.todo_list_app.global.security.repositories.UserRepo;
import com.todo_list_app.global.security.requests.LoginRequest;
import com.todo_list_app.global.security.requests.RegisterRequest;
import com.todo_list_app.global.security.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(

        RegisterRequest request

    ) throws IOException, ConflictException {

        var user = User.builder()

            .firstName(request.getFirstName())

            .lastName(request.getLastName())

            .email(request.getEmail())

            .password(passwordEncoder.encode(request.getPassword()))

            .role(Role.USER)

        .build();

        Optional<User> optionalUser = userRepo.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {

            throw new ConflictException("Username is already exists!");

        } else {

            userRepo.save(user);

            var jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse.builder()

                .token(jwtToken)

            .build();

        }

    }

    public User findUserByEmail(String username) {

        return userRepo.findByEmail(username).get();

    }

    public User findUserById(Integer id) {

        return userRepo.findById(id).get();

    }

    public Integer updateFirstName(String firstName, Integer id) {

        return userRepo.updateFirstName(firstName, id);

    }

    public Integer updateLastName(String lastName, Integer id) {

        return userRepo.updateLastName(lastName, id);

    }

    public Integer updateEmail(

        String oldEmail, String password, String newEmail, Integer id

    ) throws NotFoundException {

        User user = findUserByEmail(oldEmail);

        if (passwordEncoder.matches(password, user.getPassword())) {

            return userRepo.updateEmail(newEmail, id);

        } else {

            throw new NotFoundException("Incorrect Password!");

        }

    }

    public Integer updatePassword(

        String email, String oldPassword, String newPassword, Integer id

    ) throws NotFoundException {

        User user = findUserByEmail(email);

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {

            return userRepo.updatePassword(passwordEncoder.encode(newPassword), id);

        } else {

            throw new NotFoundException("Incorrect Old Password!");

        }

    }

    public void deleteAccount(Integer id) {

        userRepo.deleteById(id);

    }

    public AuthenticationResponse login(LoginRequest request)

        throws InvalidDataException {

        Optional<User> findByEmail = userRepo.findByEmail(request.getEmail());

        if (findByEmail.isEmpty()) {

            throw new InvalidDataException("email");

        }

        boolean checkPassword = passwordEncoder.matches(

            request.getPassword(),

            findByEmail.get().getPassword()

        );

        if (!checkPassword) {

             throw new InvalidDataException("password");

        }

        authenticationManager.authenticate(

            new UsernamePasswordAuthenticationToken(

                request.getEmail(), request.getPassword()

            )

        );

        var jwtToken = jwtService.generateToken(findByEmail.get());

        return AuthenticationResponse.builder()

            .userId(findByEmail.get().getId())

            .token(jwtToken)

        .build();

    }

}
