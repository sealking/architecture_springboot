package com.yx.dto.login;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserLoginInDto {

    private static final long serialVersionUID = 1L;
    
    // 用户名
	private String username;
	
	// 密码
	private String password;
}
