package com.sparta.todoapp.domain.todo.dto;

import com.sparta.todoapp.domain.todo.entity.Todo;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TodoResponsePage {
    private List<TodoResponseDto> todos;
    private int totalPages;
    private long totalElements;

    public TodoResponsePage(Page<Todo> page) {
        this.todos = page.getContent().stream()
                .map(Todo::to)
                .collect(Collectors.toList());
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}