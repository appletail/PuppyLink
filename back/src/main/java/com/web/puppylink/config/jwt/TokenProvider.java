package com.web.puppylink.config.jwt;

import com.web.puppylink.config.auth.PrincipalDetails;
import com.web.puppylink.model.Authority;
import com.web.puppylink.model.redis.AccessToken;
import com.web.puppylink.repository.MemberRepository;
import com.web.puppylink.service.RedisServiceImpl;
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
import org.springframework.stereotype.Component;

import com.web.puppylink.model.Member;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component("tokenProvider")
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "authorities";

    private final String secret;
    private final long accesstokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    private Key key;

	@Autowired
    MemberRepository memberRepository;

    @Autowired
    RedisServiceImpl redisService;
	
    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds
        )
    {

        this.secret = secret;
        this.accesstokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000;            //  1일
        this.refreshTokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000 * 60;      //  6일

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Authentication 객체의 권한정보를 이용해서 토근을 생성하는 함수
    public String createToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        System.out.println( "권한 : " + authorities );
        // 토큰의 익스파이어 타임을 설정
        long now = (new Date(System.currentTimeMillis())).getTime();
        Date validity = new Date(now + accesstokenValidityInMilliseconds);

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
        Date validity = new Date(now + refreshTokenValidityInMilliseconds);
        System.out.println("createRefreshToken : " + validity.getTime());
        System.out.println("createRefreshToken : " + validity);
//        System.out.println("createRefreshToken : " + validity);
        
        
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
//        System.out.println(validity);
        logger.info("createRefreshToken : {}", authentication);
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

        // 저장한 정보(객체)를 가져오기
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 객체에 있는 권한 정보를 컬렉션으로 가져오기
        Set<Authority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(Authority::new)
                        .collect(Collectors.toSet());

        // 유저 이메일만 재등록하고 저장
        PrincipalDetails principal = new PrincipalDetails(Member.builder()
                .email(claims.getSubject())
                .authorities(authorities)
                .build());

        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    public Date getExpired(String token) {
        // 저장한 정보(객체)를 가져오기
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        // 유효시간 반환
        return claims.getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch ( io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch ( ExpiredJwtException e) {
            logger.info("만료된 토큰입니다.");
        } catch ( UnsupportedJwtException e) {
            logger.info("지원하지 않는 JWT 토큰입니다.");
        } catch ( IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        } catch ( Exception e) {
            logger.error("================================================");
            logger.error("JwtFilter - doFilterInternal() 오류발생");
            logger.error("Exception Message : {}", e.getMessage());
            logger.error("Exception StackTrace : {");
            e.printStackTrace();
            logger.error("}");
            logger.error("================================================");
        }
        return false;
    }

    public Optional<AccessToken> getBlackListForAccess(String accessToken) {
            // AccessToken 저장 ( accessToken, 유효시간 )
            AccessToken token = AccessToken.builder()
                            .accessToken(accessToken)
                            .expired(getExpired(accessToken))
                            .build();
            // 블랙리스트 확인
            return redisService.findAccessToken(token);
    }
}
