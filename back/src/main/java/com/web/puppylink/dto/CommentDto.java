package com.web.puppylink.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	@ApiModelProperty(dataType = "int", example = "1")
	private int     comentNo;
	@ApiModelProperty(dataType = "String", example = "댓글 내용")
	private String  letter;
    @ApiModelProperty(dataType = "String", example = "2023-02-09")
    private String regDate;
	@ApiModelProperty(dataType = "String", example = "1")
	private int  boardNo;
	@ApiModelProperty(dataType = "String", example = "admin@gmail.com")
	private String  email;
}
