package com.example.schedule.service;

import com.example.schedule.dto.Author.CreateAuthorRequest;
import com.example.schedule.entity.AuthorEntity;
import com.example.schedule.repository.Author.IAuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final IAuthorRepository authorRepository;

    public AuthorService(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorEntity createAuthor(CreateAuthorRequest request) {
        AuthorEntity author = new AuthorEntity(request.getEmail());
        return authorRepository.save(author);
    }
}
