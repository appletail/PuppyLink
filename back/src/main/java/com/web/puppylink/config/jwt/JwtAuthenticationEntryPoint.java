package com.web.puppylink.config.jwt;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.puppylink.config.code.TokenCode;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		String exception = request.getAttribute("exception").toString();

		if (exception == null) {
			setResponse(response, TokenCode.UNKNOWN_ERROR);
		}
		// 잘못된 타입의 토큰인 경우
		else if (exception.equals(TokenCode.WRONG_TYPE_TOKEN.getCode() + "")) {

			setResponse(response, TokenCode.WRONG_TYPE_TOKEN);
		}
		// 토큰 만료된 경우
		else if (exception.equals(TokenCode.EXPIRED_TOKEN.getCode() + "")) {

			setResponse(response, TokenCode.EXPIRED_TOKEN);
		}
		// 지원되지 않는 토큰인 경우
		else if (exception.equals(TokenCode.UNSUPPORTED_TOKEN.getCode() + "")) {

			setResponse(response, TokenCode.UNSUPPORTED_TOKEN);
		} else {
			setResponse(response, TokenCode.ACCESS_DENIED);
		}
	}

	// 한글 출력을 위해 getWriter() 사용
	private void setResponse(HttpServletResponse response, TokenCode code) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		JSONObject responseJson = new JSONObject();
		responseJson.put("message", code.getMessage());
		responseJson.put("code", code.getCode());

		response.getWriter().print(responseJson);
	}
}
