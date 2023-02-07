package com.web.puppylink.config.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonCode {

    JOIN_MEMBER(2001, "봉사자로 회원가입이 되었습니다."),
    JOIN_FOUNDATION(2002, "재단으로 회원가입이 되었습니다."),
    SUCCESS_LOGIN(2003, "로그인이 성공하였습니다."),
    FAILED_LOGIN(2004, "로그인이 실패하였습니다."),
    SUCCESS_SECESSION(2005, "회원탈퇴가 정상적으로 되었습니다."),
    FAILED_SECESSION(2006, "회원탈퇴가 이루어지지 않았습니다."),
    SUCCESS_MAIL(2007, "메일 전송이 되었습니다."),
    INDEX_NOT_FOUND(2008, "인덱스가 존재하지 않습니다."),
    BOARD_NOT_FOUND(2009, "게시글을 찾을 수 없습니다.");

    private int code;
    private String message;


}