package com.web.puppylink.service;

import com.web.puppylink.config.jwt.TokenProvider;
import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.dto.PasswordDto;
import com.web.puppylink.dto.TokenDto;
import com.web.puppylink.model.Member;
import com.web.puppylink.model.redis.AccessToken;
import com.web.puppylink.model.redis.Auth;
import com.web.puppylink.model.redis.RefreshToken;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Optional;

public interface MemberService {

    Member signup(MemberDto member) throws Exception;
    TokenDto getTokenByAuthenticateion(Authentication authentication);
    Optional<Member> getMemberWithAuthorities(String email);
    Optional<Member> getMyMemberWithAuthorities();
	boolean duplicateCheckEmail(String email);
	boolean duplicateCheckNickName(String nickName);
//    void updateRefresh(String email, String refresh);
//    Map<String, String> refresh(Authentication authentication);
    Object update(PasswordDto newPassword, String nickName);
    void deleteMemberByToken(TokenDto token) throws Exception;
    AccessToken getAccessEntityByToken(String accessToken);
    RefreshToken getRefreshEntityByToken(String refreshToken);

}
