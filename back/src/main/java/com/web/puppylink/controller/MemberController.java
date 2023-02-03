package com.web.puppylink.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.puppylink.config.jwt.JwtFilter;
import com.web.puppylink.config.jwt.TokenProvider;
import com.web.puppylink.config.util.MailUtil;
import com.web.puppylink.dto.LoginDto;
import com.web.puppylink.dto.MailDto;
import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.dto.TokenDto;
import com.web.puppylink.model.BasicResponse;
import com.web.puppylink.model.Member;
import com.web.puppylink.service.MemberService;
import com.web.puppylink.service.RedisServiceImpl;

import io.lettuce.core.RedisException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:8081" })
@RestController
@RequestMapping("/members")
public class MemberController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService memberService;
    private final JavaMailSender javaMailSender;
    private final RedisServiceImpl redisService;
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    public MemberController(
            TokenProvider tokenProvider,
            AuthenticationManagerBuilder authenticationManagerBuilder,
            MemberService memberService,
            JavaMailSender javaMailSender,
            RedisServiceImpl redisService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.memberService = memberService;
        this.javaMailSender = javaMailSender;
        this.redisService = redisService;
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestBody LoginDto login) {

        logger.debug("MembersController login intro : {}", login);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        //refresh토큰을 DB에 저장합니다
        memberService.updateRefresh(authentication.getName(), refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
        httpHeaders.add("refreshToken", "Bearer " + refreshToken);

        return new ResponseEntity<>(new TokenDto("Bearer " +accessToken, "Bearer " + refreshToken), httpHeaders, HttpStatus.OK);
    }
    
    @GetMapping("/reissuance")
    public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }
        String refreshToken = authorizationHeader.substring(7);
        Map<String, String> tokens = memberService.refresh(refreshToken, tokenProvider);
        System.out.println("accessToken : " + tokens.get("accessToken"));
        System.out.println("refreshToken : " + tokens.get("refreshToken"));
//        if(tokens.get("refreshToken") != null) {
//        	memberService.updateRefresh(authorizationHeader, tokens.get("refreshToken"));
//        }

        response.setHeader("accessToken", tokens.get("accessToken"));
        if (tokens.get("refreshToken") != null) {
            response.setHeader("refreshToken", tokens.get("refreshToken"));
        }


//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
//        httpHeaders.add("refreshToken", "Bearer " + refreshToken);
//
//        return new ResponseEntity<>(new TokenDto("Bearer " +accessToken, "Bearer " + refreshToken), httpHeaders, HttpStatus.OK);


        return ResponseEntity.ok(tokens);
    }
    @PostMapping("logout")
    @ApiOperation(value = "로그아웃 진행")
    public Object logout() {
        return null;
    }

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    public Object signup(@RequestBody MemberDto member) {
        if ( redisService.getConfirmAuthByEamil(member.getEmail(), member.getAuth()) ) {
            return ResponseEntity.ok(memberService.signup(member));
        } else {
            return ResponseEntity.notFound();
        }
    }

    @GetMapping("/account")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Member> getMyInfo() {
        return ResponseEntity.ok(memberService.getMyMemberWithAuthorities().get());
    }

    @GetMapping("/account/{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Member> getAdminInfo(@PathVariable String email) {
        return ResponseEntity.ok(memberService.getMemberWithAuthorities(email).get());
    }

    @PostMapping("/mail")
    @ApiOperation(value = "회원가입 인증메일 발송")
    public ResponseEntity<?> getSignupToAuthentication(@RequestBody MailDto mail) {
        logger.info("MemberController SignupToAuth : {} ", mail);
        try {
            // 인증번호 생성 및 redis 저장
            String auth = MailUtil.randomAuth();
            mail.setAuth(auth);
            redisService.saveMail(mail);
            // 가입자에게 보낼 이메일 작성
            SimpleMailMessage message = MailUtil.createMail(mail);
            javaMailSender.send(message);
            return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
        } catch ( RedisException e ) {
            e.printStackTrace();
            return new ResponseEntity<String>("FAILED",HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        } catch ( MailException e ) {
            e.printStackTrace();
            return new ResponseEntity<>("ERROR",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{nickName}/change")
    @ApiOperation(value = "비밀번호 변경")
    public ResponseEntity<Integer> update(@RequestBody String newPassword, @PathVariable String nickName) {
    	memberService.update(newPassword, nickName);
    	return new ResponseEntity<Integer>(1, HttpStatus.OK);
    }
    
    @GetMapping("/checkEmail/{email}")
    @ApiOperation(value = "이메일 중복조회")
    public boolean emailCheck(@PathVariable  String email) {
    	logger.debug("UserController duplicate email check : {}", email);
    	if(memberService.duplicateCheckEmail(email)) {
    		return false;
    	}
    	return true;
    }
    
    @GetMapping("/checkNickname/{nickName}")
    @ApiOperation(value = "닉네임 중복조회")
    public boolean nickNameCheck(@PathVariable String nickName) {
    	logger.debug("UserController duplicate nickName check : {}", nickName);
    	if(memberService.duplicateCheckNickName(nickName)){
    		return false;
    	}
    	return true;
    }
}