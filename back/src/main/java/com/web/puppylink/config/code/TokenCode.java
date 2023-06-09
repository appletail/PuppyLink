package com.web.puppylink.config.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenCode {

    UNKNOWN_ERROR(1003, "토큰이 존재하지 않습니다."),
    WRONG_TYPE_TOKEN(1004, "변조된 토큰입니다."),
    EXPIRED_TOKEN(1005, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(1006, "변조된 토큰입니다."),
    ACCESS_DENIED(1007, "권한이 없습니다."),
    WRONG_TOKEN(1008, "잘못된 토큰입니다."),
    PERMISSION_DENIED(1009, "접근 권한이 없습니다");

    private int code;
    private String message;
}
