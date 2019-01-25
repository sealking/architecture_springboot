package com.yx.entity;

import lombok.Data;

@Data
public class GetTmxxEntity {

	// ID
	private String id;
	
	// 题目编号
	private String tmbh;
	
	// 题目所属类别
	private String tmzl;
	
	// 题目类型
	private String tmlx;
	
	// 题目内容
	private String tmnr;
	
	// 题目选项
	private String tmxx;
	
	// 题目答案
	private String tmda;
	
	// 题目分值
	private String tmfz;
	
}
