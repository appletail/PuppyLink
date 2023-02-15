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
public class FoundationDto {

    @ApiModelProperty(value = "단체 소개글", required = true)
    private String description;
    
    @ApiModelProperty(value = "단체 이메일", required = true)
    private String members_email;

}
