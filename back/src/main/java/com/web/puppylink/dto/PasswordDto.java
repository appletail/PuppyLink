package com.web.puppylink.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {
	@ApiModelProperty(dataType = "int", example = "puppylinknew123")
	private String newPassword;
	@ApiModelProperty(dataType = "int", example = "puppylinkraw123")
	private String rawPassword;
}
