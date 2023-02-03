package com.web.puppylink.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {

    private String email;
    private String password;
    private String name;
    private String phone;
    private String nickName;
    
    // 단체 회원가입 정보
    private String businessNo;
    private String businessName;
    private String presidentName;
    private String startDate;

    // 인증번호
    private String auth;

}
