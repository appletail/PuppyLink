package com.web.puppylink.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.web.puppylink.config.Code;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

//AuthorizationFilter
public class JwtFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	public static final String AUTHORIZATION_HEADER = "Authorization";
	private TokenProvider tokenProvider;

	public JwtFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		String authrizationHeader = request.getHeader(AUTHORIZATION_HEADER);
		try {
			// Access Token만 꺼내옵니다
			String accessToken = resolveToken(request);
			String requestURI = request.getRequestURI();
			
	        if (servletPath.equals("/uesrs/login") || servletPath.equals("/users/refresh")) {
	            filterChain.doFilter(request, response);
	        }
			
			// accessToken 유효한지 검사
			if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
				Authentication authentication = tokenProvider.getAuthentication(accessToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다., uri: {}", authentication.getName(), requestURI);
			} else {
				logger.debug("유효한 JWT 토큰이 없습니다. uri: {}", requestURI);
			}
		} catch (SecurityException | MalformedJwtException e) {
			request.setAttribute("exception", Code.WRONG_TYPE_TOKEN.getCode());
		} catch (ExpiredJwtException e) {
			request.setAttribute("exception", Code.EXPIRED_TOKEN.getCode());
		} catch (UnsupportedJwtException e) {
			request.setAttribute("exception", Code.UNSUPPORTED_TOKEN.getCode());
		} 
		catch (IllegalArgumentException e) {
			request.setAttribute("exception", Code.WRONG_TOKEN.getCode());
		} 
		catch (Exception e) {
			logger.error("================================================");
			logger.error("JwtFilter - doFilterInternal() 오류발생");
			logger.error("Exception Message : {}", e.getMessage());
			logger.error("Exception StackTrace : {");
			e.printStackTrace();
			logger.error("}");
			logger.error("================================================");
			request.setAttribute("exception", Code.UNKNOWN_ERROR.getCode());
		}
		filterChain.doFilter(request, response);
	}

	// 토큰정보를 받아오는 함수
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
