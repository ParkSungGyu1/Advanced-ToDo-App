package com.sparta.todoapp.domain.user.entity;

import com.sparta.todoapp.common.entity.BaseTimeStamp;
import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.user.dto.MemberRequestDto;
import com.sparta.todoapp.domain.user.dto.MemberResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member extends BaseTimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String memberName;

    @Column
    private String email;

    @ManyToMany(mappedBy = "memberList")
    private List<Todo> todoList = new ArrayList<>();

    public static Member from(MemberRequestDto requestDto) {
        Member member = new Member();
        member.initData(requestDto);
        return member;
    }

    private void initData(MemberRequestDto requestDto) {
        this.memberName = requestDto.getMemberName();
        this.email = requestDto.getEmail();
    }

    public MemberResponseDto to() {
        return new MemberResponseDto(
                this.id,
                this.memberName,
                this.email
        );
    }

    public void updateData(MemberRequestDto memberRequestDto) {
        this.memberName = memberRequestDto.getMemberName();
        this.email = memberRequestDto.getEmail();
    }
}
