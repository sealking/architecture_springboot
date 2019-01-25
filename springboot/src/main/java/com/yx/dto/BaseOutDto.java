package com.yx.dto;

import lombok.Data;

@Data
public class BaseOutDto {
	
    // 返回代码
	private String returnCode;
	
	// 返回消息
	private String msg;
}
