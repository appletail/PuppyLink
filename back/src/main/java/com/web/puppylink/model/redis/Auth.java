package com.web.puppylink.model.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "Signup" , timeToLive = 180)
public class Auth implements Serializable {
    // redis hash값 = Signup timeToLive (second 단위)
    @Id
    private String email;
    private String auth;
}
