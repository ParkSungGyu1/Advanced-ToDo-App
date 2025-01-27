package com.sparta.todoapp.domain.todo.service;

import com.sparta.todoapp.domain.todo.dto.CommentRequestDto;
import com.sparta.todoapp.domain.todo.dto.CommentResponseDto;
import com.sparta.todoapp.domain.todo.entity.Comment;
import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.todo.repository.CommentRepository;
import com.sparta.todoapp.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long todoId) {
        Todo todo = todoRepository.findTodoById(todoId);
        Comment comment = Comment.from(requestDto, todo);
        Comment savedComment = commentRepository.save(comment);
        return savedComment.to();
    }

    @Transactional
    public void updateComment(CommentRequestDto requestDto, Long todoId, Long commentId) {
        todoRepository.findTodoById(todoId);
        Comment comment = commentRepository.findCommentById(commentId);
        comment.updateData(requestDto);
    }

    @Transactional
    public void deleteComment(Long todoId, Long commentId) {
        todoRepository.findTodoById(todoId);
        commentRepository.findCommentById(commentId);
        commentRepository.deleteById(commentId);
    }
}
