package com.web.puppylink.service;

import com.web.puppylink.config.SecurityUtil;
import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.model.Authority;
import com.web.puppylink.model.Foundation;
import com.web.puppylink.model.Member;
import com.web.puppylink.repository.FoundationRepository;
import com.web.puppylink.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Component("userService")
public class MemberServiceImpl implements MemberService{


    private final MemberRepository memberRepository;
    private final FoundationRepository foundationRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder, FoundationRepository foundationRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.foundationRepository = foundationRepository;
    }

    @Transactional
    @Override
    public Member signup(MemberDto member) {

        // 회원이 존재하는지 확인
        if( memberRepository.findAuthoritiesByEmail(member.getEmail()).orElse(null) != null ) {
            throw new RuntimeException("이미 가입되어 있는 유저가 존재합니다.");
        }

        Authority authority;
        Foundation foundationInfo;
        Member memberInfo;
        
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));

        // 봉사자 회원가입 
        if(member.getBusinessName() == null) {
            authority = Authority.builder()
                    .authorityName("ROLE_USER")
                    .build();
            
            memberInfo  = Member.builder()
                    .email(member.getEmail())
                    .password(passwordEncoder.encode(member.getPassword()))
                    .name(member.getName())
                    .phone(member.getPhone())
                    .nickName(member.getNickName())
                    .activated(true)
                    .joinDate(now)
                    .authorities(Collections.singleton(authority))
                    .build();
            
        // 단체 회원가입
        }else {
        	// 단체회원이 존재하는지 확인
            if( foundationRepository.findFoundationByBusinessNo(member.getBusinessNo()).orElse(null) != null ) {
                throw new RuntimeException("해당 사업자번호로 이미 가입되어 있는 단체가 존재합니다.");
            }
        	
            authority = Authority.builder()
                    .authorityName("ROLE_MANAGER")
                    .build();

            memberInfo  = Member.builder()
                    .email(member.getEmail())
                    .password(passwordEncoder.encode(member.getPassword()))
                    .name(member.getName())
                    .phone(member.getPhone())
                    .nickName(member.getNickName())
                    .activated(true)
                    .joinDate(now)
                    .authorities(Collections.singleton(authority))
                    .build();
            
            memberRepository.save(memberInfo);
            
            foundationInfo = Foundation.builder()
            		.businessNo(member.getBusinessNo())
            		.businessName(member.getBusinessName())
            		.presidentName(member.getPresidentName())
            		.startDate(member.getStartDate())
            		.email(memberInfo)
                    .build();
            
            foundationRepository.save(foundationInfo);
        }

        return memberRepository.save(memberInfo);
    }

    @Override
    public Optional<Member> getMemberWithAuthorities(String email) {
        return memberRepository.findAuthoritiesByEmail(email);
    }

    @Override
    public Optional<Member> getMyMemberWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(memberRepository::findAuthoritiesByEmail);
    }
}
