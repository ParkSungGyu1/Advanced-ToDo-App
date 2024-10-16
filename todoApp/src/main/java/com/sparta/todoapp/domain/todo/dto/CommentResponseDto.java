package com.sparta.todoapp.domain.todo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String comment;
    private String userName;

}
