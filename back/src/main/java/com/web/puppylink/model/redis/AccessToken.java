package com.web.puppylink.model.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash( value = "Access", timeToLive = 3600 )
public class AccessToken implements Serializable {
    // redis hash값 = Access timeToLive (second 단위)
    @Id
    private String accessToken;
    private Date expired;
}
