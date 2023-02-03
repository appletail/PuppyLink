package com.web.puppylink.config.util;

import com.web.puppylink.dto.MailDto;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.mail.SimpleMailMessage;

import javax.annotation.Resource;
import java.util.Random;

@NoArgsConstructor
public class MailUtil {

    private static final int leftLimit = 48;    // '0'
    private static final int rightLimit = 122;  // 'z'

    public static String randomAuth() {
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >=97 ))
                .limit(6)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public static SimpleMailMessage createMail(MailDto mail) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getEmail());
        message.setSubject("puppylink 인증번호 입니다.");
        message.setText("[인증] 본문 \n인증번호 : " + mail.getAuth());
        return message;
    }

}
