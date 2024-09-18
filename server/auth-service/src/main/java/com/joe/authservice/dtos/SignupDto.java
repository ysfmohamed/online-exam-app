package com.joe.authservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupDto {
    @NotEmpty(message = "Username should not be empty")
    private String username;

    @NotEmpty(message = "Email should not be empty")
    @Email(message="Please enter a valid email")
    private String email;

    @NotEmpty(message = "First name should not be empty")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    private String lastName;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotEmpty(message="User's role should not be empty")
    @Pattern(regexp="(?i)teacher||(?i)student", message = "Please enter your role (teacher/student)")
    private String role;

}
