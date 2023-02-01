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


}
