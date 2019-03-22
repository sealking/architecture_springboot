package com.yx.entity;

import lombok.Data;

@Data
public class GetExamListByStuNoParm {
	// 学员编号
	private String xybh;
	
	// 工种
	private String gz;
	
	// 题库编号
	private String tkbh;
}
