package com.web.puppylink.config;


import com.web.puppylink.config.jwt.TokenProvider;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.swing.text.html.Option;
import java.util.Optional;

@NoArgsConstructor
public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    public static Optional<String> getCurrentUsername() {
        // access토큰 필터에서 저장한 Authentication 객체를 받아옴
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Authentication이 없을 경우
        if( authentication == null ) {
            logger.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String useremail = null;
        // 타입 확인
        if( authentication.getPrincipal() instanceof UserDetails ) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            useremail = springSecurityUser.getUsername();
        } else if ( authentication.getPrincipal() instanceof String) {
            useremail = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(useremail);
    }
}
