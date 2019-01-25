package com.yx.entity;

import lombok.Data;

@Data
public class GetQuestionsSettingEntity {
	// 题库编号
	private String tkbh;
	
	// 题目所属类别
	private String tmzl;
	
	// 题目类型
	private String tmlx;
	
	// 难易度
	private String tmnyd;
	
	// 题目数量
	private int tmsl;
}
