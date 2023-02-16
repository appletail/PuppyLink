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
@RedisHash( value = "Refresh", timeToLive = 3600 * 24)
public class RefreshToken implements Serializable {
    // redis hash값 = Refresh timeToLive (second 단위)
    @Id
    private String email;
    private String refreshToken;
}
