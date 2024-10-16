package com.sparta.todoapp.domain.user.service;

import com.sparta.todoapp.domain.user.dto.MemberRequestDto;
import com.sparta.todoapp.domain.user.dto.MemberResponseDto;
import com.sparta.todoapp.domain.user.entity.Member;
import com.sparta.todoapp.domain.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + memberId));
        return member.to();
    }

    @Transactional
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
        Member member = Member.from(memberRequestDto);
        Member savedMember = memberRepository.save(member);
        return member.to();
    }

    @Transactional
    public void updateMember(MemberRequestDto memberRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + memberId));
        member.updateData(memberRequestDto);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + memberId));
        memberRepository.deleteById(memberId);
    }
}
