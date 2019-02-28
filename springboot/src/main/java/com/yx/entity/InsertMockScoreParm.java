package com.yx.entity;

import java.util.Date;

import lombok.Data;

@Data
public class InsertMockScoreParm {
	// 学员编号
	private String xybh;
	
	// 模拟考试设定日期
	private Date sdrq;
	
	// 培训编号
	private String pxbh;
	
	// 分数
	private double kscj;
}
