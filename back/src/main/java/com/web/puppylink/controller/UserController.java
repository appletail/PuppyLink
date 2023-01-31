package com.web.puppylink.controller;

import com.web.puppylink.config.jwt.JwtFilter;
import com.web.puppylink.config.jwt.TokenProvider;
import com.web.puppylink.dto.LoginDto;
import com.web.puppylink.dto.TokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.web.puppylink.model.BasicResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:8081" })
@RestController
@RequestMapping("/users")
public class UserController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    public UserController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/loginProc")
    @ApiOperation(value = "로그인")
    public Object login(@RequestBody LoginDto login) {

        logger.debug("UserController login intro : {}", login);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        return new ResponseEntity<>(new TokenDto(accessToken, refreshToken), httpHeaders, HttpStatus.OK);
    }

    public Object reIssueAccessToken(@RequestBody String refreshToken) {
        return null;
    }

    @PostMapping("logout")
    @ApiOperation(value = "로그아웃 진행")
    public Object logout() {
        return null;
    }


//
//    @PostMapping("/account/signup")
//    @ApiOperation(value = "회원가입")
//    public Object signup(@Valid @RequestBody SignupRequest request) {
//        // 이메일, 닉네임 중복처리 필수
//    	String uid = request.getUid();
//    	String email = request.getEmail();
//    	String password = request.getPassword();
//
//    	Optional<User> userOpt = userDao.findUserByEmailAndUid(email, uid);
//
//        // 회원가입단을 생성해 보세요.
//        if (userOpt.isPresent()) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
//        } else {
//            User user = new User();
//            LocalDateTime localDateTime = LocalDateTime.now();
//            user.setUid(uid);
//            user.setEmail(email);
//            user.setPassword(password);
//            user.setCreateDate(localDateTime);
//            userDao.save(user);
//
//            final BasicResponse result = new BasicResponse();
//            result.status = true;
//            result.data = "success";
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        }
//    }
//
//    @PutMapping("/account/pwdchange")
//    @ApiOperation(value = "비밀번호 변경")
//    public Object pwdchange(@Valid @RequestBody User user) {
//		userDao.save(user);
//
//    	final BasicResponse result = new BasicResponse();
//        result.status = true;
//        result.data = "success";
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @GetMapping("/account/uid")
//    @ApiOperation(value = "UID 중복 조회")
//    public Object uidcheck(@RequestParam(required = true) final String uid) {
//    	System.out.println("UID..");
//        Optional<User> userOpt = userDao.findUserByUid(uid);
//        ResponseEntity response = null;
//
//        if (userOpt.isPresent()) {
//            final BasicResponse result = new BasicResponse();
//            result.status = true;
//            result.data = "success";
//            response = new ResponseEntity<>(result, HttpStatus.OK);
//        } else {
//            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//
//        return response;
//    }
}