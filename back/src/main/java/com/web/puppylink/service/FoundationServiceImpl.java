package com.web.puppylink.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.web.puppylink.dto.FoundationDto;
import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.model.Authority;
import com.web.puppylink.model.Foundation;
import com.web.puppylink.model.Member;
import com.web.puppylink.model.Volunteer;
import com.web.puppylink.model.File.FileRequest;
import com.web.puppylink.repository.AuthRedisRepository;
import com.web.puppylink.repository.FoundationRepository;
import com.web.puppylink.repository.MemberRepository;
import com.web.puppylink.repository.RefreshRedisRepository;

@Component("foundationService")
public class FoundationServiceImpl implements FoundationService{
	private final MemberRepository memberRepository;
    private final FoundationRepository foundationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthRedisRepository authRedisRepository;
    private final RefreshRedisRepository refreshRedisRepository;

    public FoundationServiceImpl(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder,
            FoundationRepository foundationRepository,
            AuthRedisRepository authRedisRepository,
            RefreshRedisRepository refreshRedisRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.foundationRepository = foundationRepository;
        this.authRedisRepository = authRedisRepository;
        this.refreshRedisRepository = refreshRedisRepository;
    }

	@Override
	public List<Foundation> getFoundationAll() {
		return foundationRepository.findFoundationAllByOrderByBusinessNameDesc();
	}
	
	@Transactional
    @Override
    public Member signup(MemberDto member) {
        // 회원이 존재하는지 확인
        if( memberRepository.findAuthoritiesByEmail(member.getEmail()).orElse(null) != null ) {
            throw new RuntimeException("이미 가입되어 있는 유저가 존재합니다.");
        }

        // 인증번호가 맞는지 확인한다.
        if( !authRedisRepository.findById(member.getEmail()).get().getAuth().equals(member.getAuth()) ) {
            throw new RuntimeException("인증번호가 맞지않습니다.");
        }

        // 사업자번호로 가입한 단체회원이 존재하는지 확인
        if( foundationRepository.findFoundationByBusinessNo(member.getBusinessNo()).orElse(null) != null ) {
            throw new RuntimeException("해당 사업자번호로 이미 가입되어 있는 단체가 존재합니다.");
        }
        
        Authority authority1, authority2;
        Foundation foundationInfo;
        Member memberInfo;
        
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        authority1 = Authority.builder()
                .authorityName("ROLE_MANAGER")
                .build();
        
        authority2 = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        
        Set<Authority> authSet = new HashSet<Authority>();
        authSet.add(authority1);
        authSet.add(authority2);

        memberInfo  = Member.builder()
                .email(member.getEmail())
                .password(passwordEncoder.encode(member.getPassword()))
                .name(member.getName())
                .phone(member.getPhone())
                .nickName(member.getBusinessName())
                .activated(true)
                .joinDate(now)
                .authorities(authSet)
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

        return memberInfo;
    }

	@Transactional
	@Override
	public Foundation submitProfile(FileRequest file) {

		System.out.print(file.getNickName());
		Member member = memberRepository.findByNickName(file.getNickName()).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		
		Foundation foundation = foundationRepository.findFoundationByEmail(member).orElseThrow(()->{
			return new IllegalArgumentException("단체 정보를 찾을 수 없습니다.");
		});
		
		foundation.setProfileURL(file.getImagePath());
		
		return foundationRepository.save(foundation);
	}

	@Transactional
	@Override
	public Foundation createDescription(FoundationDto foundationDto) {

		Foundation foundation = foundationRepository.findFoundationByBusinessNo(foundationDto.getBusinessNo()).orElseThrow(()->{
			return new IllegalArgumentException("단체 정보를 찾을 수 없습니다.");
		});
		
		foundation.setDescription(foundationDto.getDescription());
        
		return foundationRepository.save(foundation);
	}
}
