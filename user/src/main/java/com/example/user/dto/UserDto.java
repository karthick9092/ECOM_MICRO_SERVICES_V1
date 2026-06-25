package com.example.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserDto {

    private Integer id;

    @NotEmpty(message = "Name must not be empty")
    @Size(min=2,max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Mobile number must not be empty")
    @Size(min=10, max=10, message = "Mobile number must be exactly 10 characters long")
    @Pattern(regexp = "^[0-9]+$", message = "Mobile number must contain only digits")
    private String mobileNumber;

}
