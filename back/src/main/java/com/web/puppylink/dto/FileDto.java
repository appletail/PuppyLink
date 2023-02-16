package com.web.puppylink.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel
public class FileDto {

    @ApiModelProperty(name = "봉사활동 번호")
    private int volunteerNo;
    @ApiModelProperty(name = "필수서류 종류")
    private String ticketType;
    @ApiModelProperty(name = "닉네임")
    private String nickName;
    @ApiModelProperty(name = "게시글 번호")
    private int boardNo;

}
