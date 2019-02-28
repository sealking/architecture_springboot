package com.yx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yx.dto.login.UserLoginInDto;
import com.yx.dto.login.UserLoginOutDto;
import com.yx.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * 登录
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/userLogin")
    public UserLoginOutDto userLogin(UserLoginInDto inDto) {
		UserLoginOutDto userLoginOutDto = loginService.userLogin(inDto);
		return userLoginOutDto;
	}

}
