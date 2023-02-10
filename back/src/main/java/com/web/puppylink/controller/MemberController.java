package com.web.puppylink.controller;

import com.web.puppylink.config.auth.PrincipalDetails;
import com.web.puppylink.config.code.CommonCode;
import com.web.puppylink.config.code.ExceptionCode;
import com.web.puppylink.config.jwt.JwtFilter;
import com.web.puppylink.config.jwt.TokenProvider;
import com.web.puppylink.config.util.MailUtil;
import com.web.puppylink.dto.BasicResponseDto;
import com.web.puppylink.dto.LoginDto;
import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.dto.TokenDto;
import com.web.puppylink.model.Member;
import com.web.puppylink.model.redis.AccessToken;
import com.web.puppylink.model.redis.Auth;
import com.web.puppylink.model.redis.RefreshToken;
import com.web.puppylink.service.FoundationServiceImpl;
import com.web.puppylink.service.MemberServiceImpl;
import com.web.puppylink.service.RedisServiceImpl;
import com.web.puppylink.service.VolunteerServiceImpl;

import io.lettuce.core.RedisException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized", response = ResponseEntity.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ResponseEntity.class),
        @ApiResponse(code = 404, message = "Not Found", response = ResponseEntity.class),
        @ApiResponse(code = 500, message = "Failure", response = ResponseEntity.class)
})
//@CrossOrigin(origins = { "http://localhost:8081" })
@RestController
@RequestMapping("/members")
public class MemberController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberServiceImpl memberService;
    private final FoundationServiceImpl foundationService;
    private final VolunteerServiceImpl volunteerService;
    private final JavaMailSender javaMailSender;
    private final RedisServiceImpl redisService;
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    public MemberController(
            TokenProvider tokenProvider,
            AuthenticationManagerBuilder authenticationManagerBuilder,
            MemberServiceImpl memberService,
            FoundationServiceImpl foundationService,
            VolunteerServiceImpl volunteerService,
            JavaMailSender javaMailSender,
            RedisServiceImpl redisService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.memberService = memberService;
        this.foundationService = foundationService;
        this.volunteerService = volunteerService;
        this.javaMailSender = javaMailSender;
        this.redisService = redisService;
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    @ApiResponse(code = 200, message = "헤더의 AccessToken & RefreshToken을 넣어둠", response = ResponseEntity.class)
    public Object login(@RequestBody LoginDto login) {

        logger.debug("MembersController login intro : {}", login);
        // 이메일과 패스워드로 Authentication인증 객체를 만들기
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        // 인증이 완료된 Authentication 객체를 저장 ( UserDetailsService를 구현한 클래스 이동 )
        // SecurityContextHolder에 인증이 완료된 Authentication객체를 저장
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenDto token = memberService.getTokenByAuthenticateion(authentication);
        // Authentication 객체를 이용해서 토큰을 생성
//        String accessToken = tokenProvider.createToken(authentication);
//        String refreshToken = tokenProvider.createRefreshToken(authentication);
        // redis에 DB저장하기
        redisService.saveRefreshToken(new RefreshToken().builder()
                .email(login.getEmail())
                .refreshToken(token.getRefreshToken())
                .build());
        // Header에 토큰을 저장
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Access-Control-Expose-Headers", JwtFilter.AUTHORIZATION_HEADER);
        httpHeaders.add("Access-Control-Expose-Headers", JwtFilter.REFRESHTOKEN_HEADER);
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token.getAccessToken());
        httpHeaders.add(JwtFilter.REFRESHTOKEN_HEADER, "Bearer " + token.getRefreshToken());
        // FE응답
        return new ResponseEntity<BasicResponseDto>(new BasicResponseDto(
                CommonCode.SUCCESS_LOGIN,memberService.getMyMemberWithAuthorities().get()), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/reissuance")
    @ApiOperation(value = "엑세스 토큰 재발급")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "재발급하기 위해서는 Token필요", response = ResponseEntity.class)
    })
    public ResponseEntity<?> reissuance(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        // refreshToken이 존재하는지 확인
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }
        // refreshtToken이 존재하면 `bearer `제외하고 토큰만 가져오기
        String refreshToken = authorizationHeader.replace("Bearer ", "");
        // 1. refreshToken이 유효성 검사
        // 1-1. 만료된 Refresh Token일시 Error Response
        if ( !tokenProvider.validateToken(refreshToken) ) {
            throw new RuntimeException("유효하지 않은 JWT토큰 입니다.");
        }
        // refreshToken에서 authentication 객체를 가져오기
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        logger.info("reissuance authentication : {}", authentication);
        logger.info("reissuance authentication : {}", authentication.getPrincipal());
        // 토큰에서 추출한 Email정보를 이용해서 비교 토큰 생성
        RefreshToken compareToken = RefreshToken.builder()
                .email(authentication.getName())
                .refreshToken(refreshToken)
                .build();
        // refreshToken이 redis에 존재하는지 검사
        if( !redisService.confirmRefreshToken(compareToken) ) {
            throw new RuntimeException("로그아웃한 계정입니다.");
        }
        // accessToken 재발급 & 저장
        String accessToken = tokenProvider.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
        httpHeaders.add(JwtFilter.REFRESHTOKEN_HEADER, "Bearer " + refreshToken);

        // 토큰 재발급 확인용 ( 서비스할 때는 삭제필수 )
        return new ResponseEntity<>(
                new TokenDto("Bearer " + accessToken, "Bearer " + refreshToken),
                httpHeaders,
                HttpStatus.OK);
    }
    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃 진행")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "정상적으로 로그아웃되었습니다.", response = BasicResponseDto.class)
    })
    public void logout(@RequestBody TokenDto tokenDto) {
        logger.info("토큰 확인하기 : {}", tokenDto);
        // AccessToken 객체 생성
        AccessToken accessToken = AccessToken.builder()
                .accessToken(tokenDto.getAccessToken())
                .expired(tokenProvider.getExpired(tokenDto.getAccessToken()))
                .build();

        // 1. token에서 토큰정보 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenDto.getRefreshToken());
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        RefreshToken refreshToken = RefreshToken.builder()
                        .email(principal.getUsername())
                        .refreshToken(tokenDto.getRefreshToken())
                        .build();
        // 2. redis에 refresh Token 삭제
        redisService.delRefreshToken(refreshToken);
        // 3. redis에 Access Token 블랙리스트 등록
        redisService.saveAccessToken(accessToken);
    }

    @PostMapping()
    @ApiOperation(value = "회원가입")
    @ApiResponses(value = {
        @ApiResponse(code=200,message="정상적으로 회원가입이 되었습니다.", response = ResponseEntity.class)
    })
    public Object signup(@RequestBody MemberDto member) {
        logger.info("회원가입에 필요한 회원 정보 : {}", member);
    	try {
            if (member.getBusinessName() == null || member.getBusinessName().equals("")) {
                // 봉사자를 회원가입합니다. 
                memberService.signup(member);
                return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                        CommonCode.JOIN_MEMBER, null), HttpStatus.OK);
            } else {
                // 사업자 회원가입 합니다.
                foundationService.signup(member);
                return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                        CommonCode.JOIN_MEMBER, null), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponseDto<ExceptionCode>(
                    ExceptionCode.EXCEPTION_SIGNUP, false), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping()
    @ApiOperation(value = "회원조회")
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
    @ApiImplicitParam(name = "email", value = "사용가능한 이메일", required = true)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "인증번호가 정상적으로 발송되었습니다.", response = ResponseEntity.class)
    })
    public ResponseEntity<?> getSignupToAuthentication(@RequestBody() Auth mail) {
        logger.info("MemberController SignupToAuth : {} ", mail);
        try {
            // 인증번호 생성 및 redis 저장
            String auth = MailUtil.randomAuth();
//            Auth mail = Auth.builder()
//                            .email(email)
//                            .auth(auth)
//                            .build();
            mail.setAuth(auth);
            redisService.saveAuth(Auth.builder()
                    .email(mail.getEmail())
                    .auth(auth)
                    .build());
            // 가입자에게 보낼 이메일 작성
            SimpleMailMessage message = MailUtil.createMail(mail);
            javaMailSender.send(message);
            return new ResponseEntity<>(new BasicResponseDto(
                    CommonCode.SUCCESS_MAIL,null),HttpStatus.OK);
        } catch ( RedisException e ) {
            e.printStackTrace();
            return new ResponseEntity<>(new BasicResponseDto(
                    ExceptionCode.EXCEPTION_DATA,null),HttpStatus.EXPECTATION_FAILED);
        } catch ( MailException e ) {
            e.printStackTrace();
            return new ResponseEntity<>(new BasicResponseDto<>(
                    ExceptionCode.EXCEPTION_MAIL,null),HttpStatus.BAD_REQUEST);
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
    @ApiImplicitParam(name = "email", value = "이메일", required = true, dataType = "String", defaultValue = "user@gmail.com")
    @ApiResponses(value = {
            @ApiResponse(code=200, message="사용가능한 이메일입니다.", response = ResponseEntity.class)
        })
    public Object emailCheck(@PathVariable  String email) {
    	logger.debug("UserController duplicate email check : {}", email);
    	try {
    		if(memberService.duplicateCheckEmail(email)) {
                return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                        CommonCode.DUPLICATE_EMAIL, false), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                        CommonCode.SUCCESS_EMAIL, true), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponseDto<ExceptionCode>(
                    ExceptionCode.EXCEPTION_DATA, null), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/checkNickname/{nickName}")
    @ApiOperation(value = "닉네임 중복조회")
    @ApiImplicitParam(name = "nickName", value = "닉네임", required = true, dataType = "String", defaultValue = "selly")
    @ApiResponses(value = {
            @ApiResponse(code=200, message="사용가능한 닉네임입니다.", response = ResponseEntity.class)
        })
    public Object nickNameCheck(@PathVariable String nickName) {
    	logger.debug("UserController duplicate nickName check : {}", nickName);
    	try {
    		if(memberService.duplicateCheckNickName(nickName)) {
                return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                		CommonCode.DUPLICATE_NICKNAME, false), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                		CommonCode.SUCCESS_NICKNAME, true), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponseDto<ExceptionCode>(
                    ExceptionCode.EXCEPTION_DATA, null), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping
    @ApiOperation( value = "회원탈퇴" )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "회원탈퇴 성공", response = ResponseEntity.class)
    })
    public Object secession(TokenDto tokenDto) {
        try {
            // s3 필수서류 삭제 
            volunteerService.deleteALLFile(tokenDto);
            memberService.deleteMemberByToken(tokenDto);
            return new ResponseEntity<>(new BasicResponseDto<>(
                    CommonCode.SUCCESS_SECESSION,null),HttpStatus.OK);
        } catch ( Exception e ) {
            e.printStackTrace();
            return new ResponseEntity<>(new BasicResponseDto<>(
                    CommonCode.FAILED_SECESSION,null),HttpStatus.BAD_REQUEST);
        }
    }
}