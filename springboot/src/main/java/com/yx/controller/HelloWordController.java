package com.yx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HelloWordController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/hello")
    public String index() {
		for(int i = 0; i < 100000; i++) {
			logger.info("i" + i);
		}
		
        return "Hello World";
    }
}
