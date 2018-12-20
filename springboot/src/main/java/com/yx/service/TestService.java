package com.yx.service;

import java.util.List;

import com.yx.entity.Test;

public interface TestService {
	public Test getTestInfoById(String id);
	
	public List<Test> getAllTestInfo();
	
	public boolean insertTest(Test test);
}
