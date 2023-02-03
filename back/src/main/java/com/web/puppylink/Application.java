package com.web.puppylink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling		// 스케줄 태스크 실행하기 위해 추가해줘야함
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
