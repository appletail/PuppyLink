package com.web.puppylink.dto;

import com.web.puppylink.model.Member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
	@ApiModelProperty(dataType = "int", example = "1")
	private int     boardNo;
	@ApiModelProperty(dataType = "String", example = "게시글 제목입니다.")
	private String  subject;
	@ApiModelProperty(dataType = "String", example = "게시글 내용입니다.")
	private String  contents;
	@ApiModelProperty(dataType = "String", example = "게시글 링크입니다.")
	private String  pictureURL;
	@ApiModelProperty(dataType = "String", example = "0")
	private String  likes;
	@ApiModelProperty(dataType = "String", example = "2023-02-06")
	private String  regDate;
	@ApiModelProperty(dataType = "String", example = "admin")
	private String  email;
}
