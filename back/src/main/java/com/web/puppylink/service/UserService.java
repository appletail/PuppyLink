package com.web.puppylink.service;

import java.util.Map;
import java.util.Optional;

import com.web.puppylink.config.jwt.TokenProvider;
import com.web.puppylink.model.Member;

public interface UserService {
	void updateRefresh(String email, String refresh);
	Map<String, String> refresh(String refreshToken, TokenProvider tokenProvider);
	public Optional<Member> getMyMemberWithAuthorities();
}
