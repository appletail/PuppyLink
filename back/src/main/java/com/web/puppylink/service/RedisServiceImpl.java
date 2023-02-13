package com.web.puppylink.service;

import com.web.puppylink.model.redis.AccessToken;
import com.web.puppylink.model.redis.Auth;
import com.web.puppylink.model.redis.RefreshToken;
import com.web.puppylink.repository.AccessRedisRepository;
import com.web.puppylink.repository.AuthRedisRepository;
import com.web.puppylink.repository.RefreshRedisRepository;
import io.lettuce.core.RedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("redisService")
public class RedisServiceImpl implements RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    private AuthRedisRepository authRedisRepository;
    private RefreshRedisRepository refreshRedisRepository;
    private AccessRedisRepository accessRedisRepository;
    @Autowired
    public RedisServiceImpl(
            AuthRedisRepository authRedisRepository,
            RefreshRedisRepository refreshRedisRepository,
            AccessRedisRepository accessRedisRepository) {
        this.authRedisRepository = authRedisRepository;
        this.refreshRedisRepository = refreshRedisRepository;
        this.accessRedisRepository = accessRedisRepository;
    }

    @Override
    public void saveAuth(Auth auth) {
        authRedisRepository.save(auth);
    }

    @Override
    public Optional<Auth> findAuth(String email) {
        return authRedisRepository.findById(email);
    }

    @Override
    public void saveRefreshToken(RefreshToken token) {
        refreshRedisRepository.save(token);
    }

    @Override
    public boolean confirmRefreshToken(RefreshToken token) {
        return refreshRedisRepository.findById(token.getEmail())
                .get()
                .getRefreshToken()
                .equals(token.getRefreshToken());
    }

    public void delRefreshToken(RefreshToken token) {
        refreshRedisRepository.delete(token);
    }

    @Override
    public void saveAccessToken(AccessToken acess) {
        accessRedisRepository.save(acess);
    }

    @Override
    public Optional<AccessToken> findAccessToken(AccessToken access) {
        return accessRedisRepository.findById(access.getAccessToken());
    }

}
