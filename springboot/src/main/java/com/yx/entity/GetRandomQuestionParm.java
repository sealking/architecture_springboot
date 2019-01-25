package com.yx.entity;

import lombok.Data;

@Data
public class GetRandomQuestionParm {
	
	// 题目所属类别
	private String tmzl;
		
	// 题目类型
	private String tmlx;
	
	// 题目难易度
	private String tmnyd;
	
	// 数量
	private int tmsl;
}
