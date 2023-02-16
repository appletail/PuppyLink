package com.web.puppylink.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.web.puppylink.config.jwt.JwtAccessDeniedHandler;
import com.web.puppylink.config.jwt.JwtAuthenticationEntryPoint;
import com.web.puppylink.config.jwt.JwtSecurityConfig;
import com.web.puppylink.config.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CorsConfig corsConfig;
	
    private final TokenProvider tokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	
    public SecurityConfiguration(TokenProvider tokenProvider,
                                 JwtAccessDeniedHandler jwtAccessDeniedHandler,
                                 JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.tokenProvider = tokenProvider;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        		.addFilter(corsConfig.corsFilter())                     // cors 설정
        		.csrf().disable()                                       // CSRF 설정을 해제

                .exceptionHandling()                                    // 인증/인가 예외처리
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)  // 인증 예외처리
                .accessDeniedHandler(jwtAccessDeniedHandler)            // 인가 예외처리

                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // spring 세션을 사용하지 않는다고 설정

                .and()
                .authorizeRequests()                                    // httpservletrequest로 접근하는 것을 제한하겠다.
                .antMatchers("/members/login").permitAll()   // 해당 uri로 접근은 인증을 하지 않겠다.!
                .antMatchers("/members/signup").permitAll()
                .antMatchers("/members/mail").permitAll()
                .antMatchers("/members/checkEmail").permitAll()
                .antMatchers("/members/checkNickname").permitAll()
                .antMatchers("/members/reissuance").permitAll()
                .antMatchers("/foundation/validate").permitAll()
                .antMatchers("/swagger/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/**").permitAll()                  // 모든 URL 허용
                .anyRequest().authenticated()                               // 나머지는 인증을 해야한다.

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }
}
