package com.sparta.todoapp.domain.todo.entity;

import com.sparta.todoapp.common.entity.BaseTimeStamp;
import com.sparta.todoapp.domain.todo.dto.CommentRequestDto;
import com.sparta.todoapp.domain.todo.dto.CommentResponseDto;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Comment extends BaseTimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String comment;

    @Column
    private String userName;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    public static Comment from(CommentRequestDto requestDto, Todo todo) {
        Comment comment = new Comment();
        comment.initData(requestDto, todo);
        return comment;
    }

    private void initData(CommentRequestDto requestDto, Todo todo) {
        this.comment= requestDto.getComment();
        this.userName= requestDto.getUserName();
        this.todo = todo;
    }

    public CommentResponseDto to() {
        return new CommentResponseDto(
                this.id,
                this.comment,
                this.userName
        );
    }

    public void updateData(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
        this.userName = requestDto.getUserName();
    }
}
