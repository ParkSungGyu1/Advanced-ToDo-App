package com.sparta.todoapp.domain.user.repository;

import com.sparta.todoapp.domain.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    default void findUserByEmail(String email){
        Optional<Member> checkMember = findByEmail(email);
        if(checkMember.isEmpty()){
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }
    }

}
