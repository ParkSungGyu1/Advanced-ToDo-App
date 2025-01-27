package com.sparta.todoapp.domain.todo.repository;

import com.sparta.todoapp.domain.todo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findCommentById(Long id){
        return findById(id).orElseThrow(() -> new IllegalArgumentException("Todo not found with id:" + id));
    }
}
