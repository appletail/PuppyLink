package com.web.puppylink.dto;

import com.web.puppylink.config.code.Code;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicResponseDto<T> {
    @ApiModelProperty(name = "Code를 참조해주세요.", value = "code", position = 1)
    public T code;
    @ApiModelProperty(name = "복수 전송" ,value = "data", position = 2)
    public Object data;
}
