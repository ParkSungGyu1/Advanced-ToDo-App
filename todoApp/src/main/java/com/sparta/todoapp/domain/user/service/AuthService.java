package com.sparta.todoapp.domain.user.service;

import com.sparta.todoapp.common.auth.JwtUtil;
import com.sparta.todoapp.common.auth.PasswordEncoder;
import com.sparta.todoapp.domain.user.dto.AuthRequestDto;
import com.sparta.todoapp.domain.user.entity.Member;
import com.sparta.todoapp.domain.user.entity.UserRoleEnum;
import com.sparta.todoapp.domain.user.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Transactional
    public void signUp(AuthRequestDto requestDto, HttpServletResponse response) {

        requestDto.initPassword(passwordEncoder.encode(requestDto.getPassword()));
        Optional<Member> checkMember = memberRepository.findByEmail(requestDto.getEmail());
        if(!checkMember.isEmpty()){
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        //memberRepository.findUserByEmail(requestDto.getEmail());

        UserRoleEnum role = UserRoleEnum.getRole(requestDto.isAdmin());
        Member signUpMember = Member.from(requestDto, role);

        Member savedUser = memberRepository.save(signUpMember);

        String token = jwtUtil.createToken(savedUser.getEmail(), savedUser.getRole());
        jwtUtil.addJwtToCookie(token, response);
    }

    public void login(AuthRequestDto requestDto, HttpServletResponse response) {
        Member checkMember = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if(!passwordEncoder.matches(requestDto.getPassword(), checkMember.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(checkMember.getEmail(), checkMember.getRole());
        jwtUtil.addJwtToCookie(token, response);
    }
}
