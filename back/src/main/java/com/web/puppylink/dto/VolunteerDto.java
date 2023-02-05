package com.web.puppylink.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerDto {
    @ApiModelProperty(dataType = "String", example = "캐나다 토론토")
    private String dest;
    @ApiModelProperty(dataType = "String", example = "2023-02-04")
    private String depTime;
    @ApiModelProperty(dataType = "String", example = "Korean Air")
    private String flightName;
    @ApiModelProperty(dataType = "String", example = "1148209801")
    private String businessNo;
    @ApiModelProperty(dataType = "String", example = "admin")
    private String email;
}
