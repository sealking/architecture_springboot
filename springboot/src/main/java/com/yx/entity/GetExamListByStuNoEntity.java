package com.yx.entity;

import java.util.Date;

import lombok.Data;

@Data
public class GetExamListByStuNoEntity {

	// 学员编号
	private String xybh;
	
	// 题库编号
	private String tkbh;
	
	// 培训编号
	private String pxbh;
	
	// 设定时间
	private Date sdrq;
}
