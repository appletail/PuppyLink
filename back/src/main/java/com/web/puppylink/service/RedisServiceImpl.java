package com.web.puppylink.service;

import com.web.puppylink.controller.MemberController;
import com.web.puppylink.dto.MailDto;
import io.lettuce.core.RedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("redisService")
public class RedisServiceImpl implements RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    private final RedisTemplate<String,Object> redisTemplate;
    private static final String EMAIL_HASH = "email:";
    @Autowired
    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public void saveMail(MailDto mail) throws RedisException {
        HashOperations<String, String, String> redisMap = redisTemplate.opsForHash();
        redisMap.put(EMAIL_HASH, mail.getEmail(), mail.getAuth());
        logger.debug("RedisService 이메일 저장 값 : {}", redisMap.get(EMAIL_HASH, mail.getEmail()));
    }

    @Override
    public void saveToken() throws RedisException {
        SetOperations<String, Object> setOpr = redisTemplate.opsForSet();
    }

    @Override
    public boolean getConfirmAuthByEamil(String email,String auth) {
        logger.info("RedisService redis compare value : {} , auth : {}", email, auth);
        HashOperations<String, String, String> redisMap = redisTemplate.opsForHash();
        String confirmAuth = redisMap.get(EMAIL_HASH, email);
        if (confirmAuth.equals(auth)) {
            logger.info("RedisService redis delete value : {}", email);
            redisMap.delete(EMAIL_HASH, email);
            return true;
        }
        return false;
    }
}
