package com.web.puppylink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableScheduling		// 스케줄 태스크 실행하기 위해 추가해줘야함
@EnableRedisHttpSession	// redis의 세션기능을 사용하기 위해 추가 하였습니다.
@EnableCaching			// redis의 쿠키기능을 사용하기 위해 추가 하였습니다.
@SpringBootApplication
public class Application {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "classpath:aws.yml";

	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
		new SpringApplicationBuilder(Application.class)
        .properties(APPLICATION_LOCATIONS)
        .run(args);
	}

}
