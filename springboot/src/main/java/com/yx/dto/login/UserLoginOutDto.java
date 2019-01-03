package com.yx.dto.login;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserLoginOutDto {
	
    private static final long serialVersionUID = 1L;
    
    // 用户名
	private String returnCode;
	
	// 密码
	private String msg;
}
