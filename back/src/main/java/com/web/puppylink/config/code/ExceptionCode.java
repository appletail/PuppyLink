package com.web.puppylink.config.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    EXCEPTION_SIGNUP(5001, "회원가입에 실패하였습니다."),
    EXCEPTION_DATA(5002,"DB 관련 오류가 발생하였습니다."),
    EXCEPTION_MAIL(5003,"메일 전송하면서 오류가 발생하였습니다.");

    private int code;
    private String message;
}
