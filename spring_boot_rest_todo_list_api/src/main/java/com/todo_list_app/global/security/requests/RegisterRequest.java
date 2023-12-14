package com.todo_list_app.global.security.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "First Name can't be blank or null")
    private String firstName;

    @NotBlank(message = "Last Name can't be blank or null")
    private String lastName;

    @Email(message = "Invalid email address!")
    private String email;

    @Pattern(

        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$",

        message = "Should contain at least a capital letter, a small letter," +

            " a number, a special character And 8 as a minimum length!"

    )
    private String password;

}
