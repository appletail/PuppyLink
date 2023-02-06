package com.web.puppylink.service;

import com.web.puppylink.model.redis.AccessToken;
import com.web.puppylink.model.redis.Auth;
import com.web.puppylink.model.redis.RefreshToken;

import javax.persistence.Access;
import java.util.Optional;

public interface RedisService {

    void saveAuth(Auth auth);
    void saveRefreshToken(RefreshToken refresh);
    boolean confirmRefreshToken(RefreshToken refresh);
    public void delRefreshToken(RefreshToken token);
    void saveAccessToken(AccessToken acess);
    Optional<AccessToken> findAccessToken(AccessToken access);

}
