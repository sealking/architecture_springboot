package com.yx.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yx.dto.login.UserLoginInDto;
import com.yx.dto.login.UserLoginOutDto;
import com.yx.entity.Test;

@RestController
@RequestMapping("/login")
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/login123")
    public UserLoginOutDto index(UserLoginInDto userLoginInDto) {
		UserLoginOutDto userLoginOutDto = new UserLoginOutDto();
		userLoginOutDto.setReturnCode("0");
		userLoginOutDto.setMsg("登录成功");
		return userLoginOutDto;
	}
	
	
	
}
