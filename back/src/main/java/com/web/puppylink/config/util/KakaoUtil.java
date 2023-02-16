package com.web.puppylink.config.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.dto.TokenDto;
import com.web.puppylink.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class KakaoUtil {

    private static final Logger logger = LoggerFactory.getLogger(KakaoUtil.class);

    private static final String TOKENAUTH = "https://kauth.kakao.com/oauth/token";
    private static final String PROFILEAPI = "https://kapi.kakao.com/v2/user/me";
    private static final String LOGOUT = "https://kapi.kakao.com/v1/user/logout";
    @Value("${api.kaKey}")
    private static String KEY;
    @Value("${api.kaAdmin}")
    private static String ADMIN_KEY;
//    private static final String REQUEST = "http://localhost:3000/Social/kakao";
    private static final String REQUEST = "http://i8c107.p.ssafy.io:3000/Social/kakao";

    // 카카오 로그인해서 얻은 인가코드롤 AccessToken 및 refresh 토큰 얻는 함수
    public static TokenDto getAccessTokenByKakao(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap();

        params.add("grant_type","authorization_code");
        params.add("client_id", KEY);
        params.add("redirect_uri", REQUEST);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(TOKENAUTH, request, String.class);

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response.getBody());

        String accessToken = element.getAsJsonObject().get("access_token").getAsString();
        String refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

        return new TokenDto(accessToken, refreshToken);
    }

    // 카카오 토큰에서 필요한 회원 정보를 가져오는 함수
    public static MemberDto getUserByAccessToken(String accessToken) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        String response = restTemplate.postForObject(PROFILEAPI, request, String.class);

        return setMemberByUser(response);

    }
    
    // 카카오 유저정보를 puppy회원에 필요한 정보만 가져오는 부분
    private static MemberDto setMemberByUser(String user) throws JsonProcessingException {
        logger.info("카카오 토큰에서 가져온 정보1 : {}", user);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(user);

        JsonObject kakaoUser = element.getAsJsonObject();
        JsonObject properties = kakaoUser.get("properties").getAsJsonObject();
        JsonObject kakao_account = kakaoUser.get("kakao_account").getAsJsonObject();

//        ObjectMapper objectMapper = new ObjectMapper();
//        KakaoDto kakaoDto = objectMapper.readValue(userInfo, KakaoDto.class);
        logger.debug("카카오 토큰에서 가져온 정보 properties : {}", kakaoUser.get("properties"));
        logger.debug("카카오 토큰에서 가져온 정보 account : {}", kakaoUser.get("kakao_account"));

        MemberDto member = new MemberDto();
        member.setEmail(kakao_account.get("email").getAsString());
        member.setPassword("social_" + kakaoUser.get("id").getAsString());
        member.setPhone("00000000000");
        member.setName(properties.get("nickname").getAsString() + kakaoUser.get("id").getAsString());
        member.setNickName(properties.get("nickname").getAsString());

        return member;
    }

    public static String logoutToKakao(Member member) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + ADMIN_KEY);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap();

        params.add("target_id_type","user_id");
        params.add("target_id", member.getName().replace(member.getNickName(),""));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(LOGOUT, request, String.class);

        return "SUCCESS";
    }

}
