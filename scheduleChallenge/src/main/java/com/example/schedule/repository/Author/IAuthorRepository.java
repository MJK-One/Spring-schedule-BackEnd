package com.example.schedule.repository.Author;

import com.example.schedule.entity.AuthorEntity;

public interface IAuthorRepository {
    AuthorEntity save(AuthorEntity author);
    AuthorEntity findById(Long id);
}