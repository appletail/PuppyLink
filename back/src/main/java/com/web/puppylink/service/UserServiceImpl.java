package com.web.puppylink.service;

import com.web.puppylink.config.SecurityUtil;
import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.model.Authority;
import com.web.puppylink.model.Foundation;
import com.web.puppylink.model.Member;
import com.web.puppylink.repository.FoundationRepository;
import com.web.puppylink.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Component("userService")
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final FoundationRepository foundationRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, FoundationRepository foundationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.foundationRepository = foundationRepository;
    }

    @Transactional
    @Override
    public Member signup(MemberDto member) {

        // 회원이 존재하는지 확인
        if( userRepository.findAuthoritiesByEmail(member.getEmail()).orElse(null) != null ) {
            throw new RuntimeException("이미 가입되어 있는 유저가 존재합니다.");
        }

        Authority authority;
        Foundation foundationInfo;
        Member memberInfo;
        
        Date date = new Date();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-mm-dd");
        String now = simple.format(date);

        
        // 봉사자 회원가입 
        if(member.getBusinessName() == null) {
            authority = Authority.builder()
                    .authorityNo("ROLE_USER")
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
                    .authorityNo("ROLE_MANAGER")
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
            
            userRepository.save(memberInfo);
            
            foundationInfo = Foundation.builder()
            		.businessNo(member.getBusinessNo())
            		.businessName(member.getBusinessName())
            		.presidentName(member.getPresidentName())
            		.startDate(member.getStartDate())
            		.email(memberInfo)
                    .build();
            
            foundationRepository.save(foundationInfo);
        }
        
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
