package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuthorEntity {
    private Long authorId; // 작성자 ID
    private String email; // 이메일
    private LocalDateTime createPostTime; // 작성일
    private LocalDateTime updatePostTime; // 수정일

    public AuthorEntity(String email) {
        this.email = email;
    }
}
