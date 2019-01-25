package com.yx.entity;

import java.util.Date;

import lombok.Data;

@Data
public class InsertQuestionsHistoryParm {

	// ID
	private String id;
	
	// 题目编号
	private String tkbh;
	
	// 题目编号
	private String tmbh;
	
	// 学员编号
	private String xybh;
	
	// 考试编号
	private String ksbh;
	
	// 设定日期
	private Date sdrq;

}
