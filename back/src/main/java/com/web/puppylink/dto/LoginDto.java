package com.web.puppylink.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @ApiModelProperty(value = "회원 이메일", example = "user@gmail.com",required = true)
    private String email;

    @ApiModelProperty(required = true)
    private String password;

}
