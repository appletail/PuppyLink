package com.web.puppylink.repository;

import com.web.puppylink.model.redis.AccessToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccessRedisRepository extends CrudRepository<AccessToken, String> {

    @Override
    Optional<AccessToken> findById(String access);
}
