package com.web.puppylink.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class GpsDto {
	//위도 , 경도, 방향, 출발공항(위도 경도), 도착공항(위도 경도)
    @ApiModelProperty(name = "항공기 위도", required = true, example = "0")
    private String lat;
    @ApiModelProperty(name = "항공기 경도", required = true, example = "0")
    private String lng;
    @ApiModelProperty(name = "방향", required = true, example = "0")
    private String dir;
    @ApiModelProperty(name = "항공기 구분자", required = true, example = "0")
    private String FlightId;

    @ApiModelProperty(name = "출발 공항 구분자", required = true, example = "0")
    private String depId;
    @ApiModelProperty(name = "도착 공항 구분자", required = true, example = "0")
    private String arriveId;
    
}
