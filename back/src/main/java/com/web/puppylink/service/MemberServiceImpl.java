package com.web.puppylink.service;

import com.web.puppylink.config.SecurityUtil;
import com.web.puppylink.config.jwt.TokenProvider;
import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.model.Authority;
import com.web.puppylink.model.Foundation;
import com.web.puppylink.model.Member;
import com.web.puppylink.repository.FoundationRepository;
import com.web.puppylink.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component("memberService")
public class MemberServiceImpl implements MemberService{


    private final MemberRepository memberRepository;
    private final FoundationRepository foundationRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder,
            FoundationRepository foundationRepository) {
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

    @Override
	public boolean duplicateCheckEmail(String email) {
    	return memberRepository.existsByEmail(email);
	}

    @Override
	public boolean duplicateCheckNickName(String nickName) {
    	return memberRepository.existsByNickName(nickName);
	}

    @Override
    @javax.transaction.Transactional
    public void updateRefresh(String email, String refresh) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 실패");
        });
        member.setRefreshToken(refresh);
        //더티 체킹 : 변한 부분 DB에 자동 커밋됩니다
        System.out.println("UserServiceImpl] member.setRefreshToken(refresh) : " + member.getRefreshToken());
    }

    @Override
    public Map<String, String> refresh(String refreshToken, TokenProvider tokenProvider) {
        // 1. refresh 토큰의 유효성 검사
        // 1-1. 만료된 Refresh Token일시 Error Response
        if (!tokenProvider.validateToken(refreshToken)) {
            System.out.println("만료된 Refresh Token일시 Error Response 발생");
            return null;
        }

        String secret = "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);
        System.out.println("refresh 토큰의 복호화 과정인지 확인한다 claimsJws : " + claimsJws);

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        System.out.println("refresh의 authorities : " + authorities);

        String email = claims.getSubject();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));


        // 1-2. 유효하지 않은(DB의 refreshToken 필드 값과 같지 않을 때) Refresh Token 일시 Error Response
        if (!member.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token 입니다.");
        }

//		//2. Access Token 재발급
//		tokenProvider


//		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//				claims.getSubject(), null);

//		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        claims.getSubject(), //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                        null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                        authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String newAccessToken = tokenProvider.createToken(authentication);

        // 3. 현재시간과 Refresh Token의 만료일을 통해 남은 만료기간 계산
        // 4. Refresh Token의 남은 만료기간이 1개월 미만일 시 Refresh Token도 재발급
        long now = (new Date(System.currentTimeMillis())).getTime();


        // === Access Token 재발급 === // // 토큰의 익스파이어 타임을 설정 // Date validity = new
//		  Date(now + this.tokenValidityInMilliseconds);
//		  Date validity = new Date(now +1000 * 20);
//		  System.out.println("Access Token 재발급 에서 access토큰 유효시간" +validity);
//
//		  String accessToken = Jwts.builder() .setSubject(claims.getSubject())
//		  .claim("auth", authorities) // 정보 저장 .signWith(key, SignatureAlgorithm.HS512)
//		  .setExpiration(validity) .compact();


        Map<String, String> accessTokenResponseMap = new HashMap<>();

        // === 현재시간과 Refresh Token 만료날짜를 통해 남은 만료기간 계산 === //
        // === Refresh Token 만료시간 계산해 1개월 미만일 시 refresh token도 발급 === //
        long refreshExpireTime = claims.getExpiration().getTime();
        System.out.println("시간 관련 출력");
        System.out.println("refreshExpireTime : " + refreshExpireTime);
        long diffDays = (refreshExpireTime - now) / 1000 / (24 * 3600);
        long diffMin = (refreshExpireTime - now) / 1000 / 60;
        System.out.println("now : " + now);
        System.out.println("(refreshExpireTime - now) : " + (refreshExpireTime - now));
        System.out.println("diffMin : " + diffMin);
        if (diffMin < 5) {
            // 토큰의 익스파이어 타임을 설정
            String newRefreshToken = tokenProvider.createRefreshToken(authentication);
            accessTokenResponseMap.put("refreshToken", newRefreshToken);
            updateRefresh(claims.getSubject(), newRefreshToken);
            System.out.println("newRefreshToken 발급 : " + newRefreshToken);
        }
        accessTokenResponseMap.put("accessToken", newAccessToken);
        return accessTokenResponseMap;
    }

}
