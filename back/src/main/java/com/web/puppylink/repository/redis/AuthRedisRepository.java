package com.web.puppylink.repository.redis;

import com.web.puppylink.model.redis.Auth;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthRedisRepository extends CrudRepository<Auth, String> {
    @Override
    Optional<Auth> findById(String email);
}
