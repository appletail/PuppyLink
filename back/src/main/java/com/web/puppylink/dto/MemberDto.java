package com.web.puppylink.dto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel
public class MemberDto {

    @ApiModelProperty(name = "이메일", required = true)
    private String email;
    @ApiModelProperty(name = "비밀번호", required = true)
    private String password;
    @ApiModelProperty(name = "이름", required = true)
    private String name;
    @ApiModelProperty(name = "핸드폰", required = true)
    private String phone;
    @ApiModelProperty(name = "닉네임", required = true)
    private String nickName;
    
    // 단체 회원가입 정보
    @ApiModelProperty(name = "사업자번호", required = true)
    private String businessNo;
    @ApiModelProperty(name = "사업자이름", required = true)
    private String businessName;
    @ApiModelProperty(name = "대표자", required = true)
    private String presidentName;
    private String startDate;

    // 인증번호
    @ApiModelProperty(name = "인증번호", required = true)
    private String auth;

}
