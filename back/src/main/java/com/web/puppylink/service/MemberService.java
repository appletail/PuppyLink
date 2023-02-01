package com.web.puppylink.service;

import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.model.Member;

import java.util.Optional;

public interface MemberService {


    Member signup(MemberDto member);
    Optional<Member> getMemberWithAuthorities(String email);
    Optional<Member> getMyMemberWithAuthorities();


}
