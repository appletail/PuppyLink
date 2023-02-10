package com.web.puppylink.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {

    @ApiModelProperty(required = true)
    private String accessToken;
    @ApiModelProperty(required = true)
    private String refreshToken;

}
