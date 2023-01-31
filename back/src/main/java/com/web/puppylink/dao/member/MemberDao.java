
package com.web.puppylink.dao.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.puppylink.model.Member;

public interface MemberDao extends JpaRepository<Member, String> {
	Member getMemberByEmail(String email);

    Optional<Member> findMemberByEmailAndPassword(String email, String password);
    Optional<Member> findMemberByEmailAndNickName(String email, String nickName);
    Optional<Member> findMemberByNickName(String NickName);
}
