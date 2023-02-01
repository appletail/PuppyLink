package com.web.puppylink.service;

import com.web.puppylink.config.SecurityUtil;
import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.model.Authority;
import com.web.puppylink.model.Member;
import com.web.puppylink.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Component("userService")
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public Member signup(MemberDto member) {

        // 회원이 존재하는지 확인
        if( userRepository.findAuthoritiesByEmail(member.getEmail()).orElse(null) != null ) {
            throw new RuntimeException("이미 가입되어 있는 유저가 존재합니다.");
        }

        // 중요
        Authority authority = Authority.builder()
                .authorityNo("ROLE_USER")
                .build();

        Member memberInfo  = Member.builder()
                .email(member.getEmail())
                .password(passwordEncoder.encode(member.getPassword()))
                .name(member.getName())
                .phone(member.getPhone())
                .nickName(member.getNickName())
                .activated(true)
                .joinDate(new Date())
                .authorities(Collections.singleton(authority))
                .build();

        return userRepository.save(memberInfo);
    }

    @Override
    public Optional<Member> getMemberWithAuthorities(String email) {
        return userRepository.findAuthoritiesByEmail(email);
    }

    @Override
    public Optional<Member> getMyMemberWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findAuthoritiesByEmail);
    }
}
