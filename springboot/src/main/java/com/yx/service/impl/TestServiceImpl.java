package com.yx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yx.dao.TestMapper;
import com.yx.entity.Test;
import com.yx.entity.TestExample;
import com.yx.service.TestService;

@Service
public class TestServiceImpl implements TestService{

	@Autowired
	TestMapper testMaper;
	
	@Override
	public Test getTestInfoById(String id) {
		Test test = testMaper.selectByPrimaryKey(id);
		return test;
	}

	@Override
	@Transactional
	public boolean insertTest(Test test) {
		boolean flg = false;
		int i = testMaper.insert(test);
		if(i > 0) {
			flg = true;
		}
		return flg;
	}

	@Override
	public List<Test> getAllTestInfo() {
		
		TestExample example = new TestExample();
		//example.createCriteria();
		List<Test> list = testMaper.selectByExample(example);
		return list;
	}
}
