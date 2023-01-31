
package com.web.puppylink.dao.user;

import java.util.Optional;

import com.web.puppylink.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<Member, String> {
}
