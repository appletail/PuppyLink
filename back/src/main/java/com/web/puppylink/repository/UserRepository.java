package com.web.puppylink.repository;

import com.web.puppylink.model.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, String> {


    Optional<Member>  findUserByEmailAndPassword(String email, String password);

    @EntityGraph(attributePaths = "authorities")
    Optional<Member>  findAuthoritiesByEmail(String email);
}
