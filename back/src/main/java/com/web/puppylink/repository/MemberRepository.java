package com.web.puppylink.repository;

import com.web.puppylink.model.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
<<<<<<< HEAD
    @EntityGraph(attributePaths = "authorities")
    Optional<Member>  findAuthoritiesByEmail(String email);
    Optional<Member> findUserByEmailAndPassword(String email, String password);
    boolean existsByNickName(String nickName);				// 닉네임 중복검사
    boolean existsByEmail(String email);					// 이메일 중복검사
    
    Optional<Member> findByEmail(String email);
    Member findByNickName(String nickName);		// s3
    
	Optional<Member> findUserByEmail(String email);
    Optional<Member> findUserByEmailAndPassword(String email, String password);

    @EntityGraph(attributePaths = "authorities")
=======

	Optional<Member> findUserByEmail(String email);
    Optional<Member> findUserByEmailAndPassword(String email, String password);

    @EntityGraph(attributePaths = "authorities")
>>>>>>> 6850030f9738870a0ecc110bb0fa9c7a5d1e7059
    Optional<Member> findAuthoritiesByEmail(String email);
}
