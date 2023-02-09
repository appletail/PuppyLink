package com.web.puppylink.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GpsDto {
	//위도 , 경도, 방향, 출발공항(위도 경도), 도착공항(위도 경도)
    @ApiModelProperty(name = "위도", required = true, example = "0")
    private String lat;
    @ApiModelProperty(name = "경도", required = true, example = "0")
    private String lng;
    @ApiModelProperty(name = "방향", required = true, example = "0")
    private String dir;
}
