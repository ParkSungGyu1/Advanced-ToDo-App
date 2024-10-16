package com.sparta.todoapp.domain.user.repository;

import com.sparta.todoapp.domain.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
