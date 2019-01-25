package com.yx.entity;

import lombok.Data;

@Data
public class GetTmxxParm {

	// 学员编号
	private String studentNo;	
	
	// 题库编号
	private String questionsNo;
	
	// 设定时间
	private String settingDate;
	
	// 题目所属类别
	private String questionType;
}
