package com.web.puppylink.model.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Refresh")
public class RefreshToken {

    @Id
    private String email;
    private String refreshToken;
}
