package com.web.puppylink.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@Builder
public class AirportDto {
    
	@ApiModelProperty(name = "출발 공항 구분자", required = true, example = "0")
	private String depCity;
    @ApiModelProperty(name = "출발 공항 위도", required = true, example = "0")
    private Double depLat;
    @ApiModelProperty(name = "출발 공항 경도", required = true, example = "0")
    private Double depLng;
    
    @ApiModelProperty(name = "도착 공항 구분자", required = true, example = "0")
    private String arriveCity;
    @ApiModelProperty(name = "도착 공항 위도", required = true, example = "0")
    private Double arriveLat;
    @ApiModelProperty(name = "도착 공항 경도", required = true, example = "0")
    private Double arriveLng;
}
