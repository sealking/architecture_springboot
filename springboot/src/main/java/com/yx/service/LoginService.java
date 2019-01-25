package com.yx.service;

import com.yx.dto.login.UserLoginInDto;
import com.yx.dto.login.UserLoginOutDto;

public interface LoginService {
	// 用户登录
	public UserLoginOutDto userLogin(UserLoginInDto inDto);

}
