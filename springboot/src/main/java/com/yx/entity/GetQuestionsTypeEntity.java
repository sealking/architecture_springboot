package com.yx.entity;

import lombok.Data;

@Data
public class GetQuestionsTypeEntity {

	// 题目所属类别
	private String tmzl;
	
	// 题目类别所对应的试题数量
	private int tmsl;
}
