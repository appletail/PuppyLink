package com.web.puppylink.dto;

import io.swagger.annotations.ApiModelProperty;

public class ResponseDto<T> {
    @ApiModelProperty(value = "code", position = 1)
    public boolean code;
    @ApiModelProperty(value = "message", position = 2)
    public String message;
    @ApiModelProperty(value = "object", position = 3)
    public T object;
}
