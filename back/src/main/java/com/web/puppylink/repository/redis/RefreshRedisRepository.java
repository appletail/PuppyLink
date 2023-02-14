package com.web.puppylink.repository.redis;

import com.web.puppylink.model.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshRedisRepository extends CrudRepository<RefreshToken, String> {

    @Override
    Optional<RefreshToken> findById(String email);
}
