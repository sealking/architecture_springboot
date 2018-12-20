package com.yx.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yx.entity.Test;
import com.yx.service.TestService;

@RestController
@RequestMapping("/test")
public class HelloWordController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TestService testService;

	@RequestMapping("/hello")
    public List<Test> index(HttpServletRequest request) {
		
//		Test test = new Test();
//		
//		test.setId("id3");
//		test.setName("name3");
//		test.setSex("sex3");
//		
//		boolean flg = testService.insertTest(test);
		List<Test> list = testService.getAllTestInfo();
		
        return list;
    }
}
