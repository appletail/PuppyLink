package com.web.puppylink.config.jwt;

import com.web.puppylink.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.web.puppylink.model.Member;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component("tokenProvider")
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long tokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    private Key key;

	@Autowired
    MemberRepository memberRepository;
	
    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds
        )
    {

        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000;
        this.refreshTokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000 * 60;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Authentication 객체의 권한정보를 이용해서 토근을 생성하는 함수
    public String createToken(Authentication authentication) {

		/*
		 * System.out.println(authentication.getName() );
		 * System.out.println(authentication.getPrincipal() );
		 * System.out.println(authentication.getDetails() );
		 * System.out.println(authentication.getCredentials() );
		 */
    	
        Member member= memberRepository.findByEmail(authentication.getName()).orElseThrow(() -> {
        	return new IllegalArgumentException("해당 아이디를 찾을 수 없습니다.");
        });
        
        System.out.println(member.toString());
        System.out.println(member.getAuthorities());
        
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        System.out.println("권한 : " +authorities );
        // 토큰의 익스파이어 타임을 설정
        long now = (new Date(System.currentTimeMillis())).getTime();
        Date validity = new Date(now + 1000 * 15);

        return Jwts.builder()
                .setSubject(authentication.getName())       //
                .claim(AUTHORITIES_KEY, authorities)        // 정보 저장
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String createRefreshToken(Authentication authentication) {

        // 토큰의 익스파이어 타임을 설정
//        Date now = new Date(System.currentTimeMillis());
//        Date validity = new Date(now.getTime() + this.refreshTokenValidityInMilliseconds);
//        Date validity = new Date(now.getTime() + 1000 * 60);
        
        long now = (new Date(System.currentTimeMillis())).getTime();
        Date validity = new Date(now + 1000 * 100);
        System.out.println("createRefreshToken : " + validity.getTime());
        System.out.println("createRefreshToken : " + validity);
//        System.out.println("createRefreshToken : " + validity);
        
        
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        System.out.println(validity);
        
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)        // 정보 저장
                .signWith(key, SignatureAlgorithm.HS512)    // 사용할 암호화 알고리즘과
                .setExpiration(validity)                    // set Expire Tim0e
                .compact();
//        .setIssuedAt(now)                           // 토큰 발행 시간 정보
    }

    // 토큰을 이용해서 Authentication 객체를 리턴 받는 함수
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토근의 유효성을 검증을 수행하는 함수 ( 만료일자 확인 )
    public boolean validateToken(String token) throws ExpiredJwtException {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            logger.info("claimsJws 정보 : {}", claimsJws );
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch ( io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        }catch ( UnsupportedJwtException e) {
            logger.info("지원하지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
