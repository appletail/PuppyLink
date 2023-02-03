package com.web.puppylink.service;

import com.web.puppylink.config.jwt.TokenProvider;
import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.model.Member;

import java.util.Map;
import java.util.Optional;

public interface MemberService {


    Member signup(MemberDto member);
    Optional<Member> getMemberWithAuthorities(String email);
    Optional<Member> getMyMemberWithAuthorities();
	boolean duplicateCheckEmail(String email);
	boolean duplicateCheckNickName(String nickName);
    void updateRefresh(String email, String refresh);
    Map<String, String> refresh(String refreshToken, TokenProvider tokenProvider);


}
