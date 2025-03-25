package com.example.schedule.controller;

import com.example.schedule.dto.Author.CreateAuthorRequest;
import com.example.schedule.entity.AuthorEntity;
import com.example.schedule.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorEntity> createAuthor(@Validated @RequestBody CreateAuthorRequest request) {
        AuthorEntity createdAuthor = authorService.createAuthor(request);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }
}
