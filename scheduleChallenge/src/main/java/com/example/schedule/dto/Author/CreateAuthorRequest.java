package com.example.schedule.dto.Author;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class CreateAuthorRequest {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String email;
}
