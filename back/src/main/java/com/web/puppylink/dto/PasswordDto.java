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
public class PasswordDto {
	@ApiModelProperty(name = "새 비밀번호", required = true)
	private String newPassword;
	@ApiModelProperty(dataType = "기존 비밀번호", required = true)
	private String rawPassword;
}
