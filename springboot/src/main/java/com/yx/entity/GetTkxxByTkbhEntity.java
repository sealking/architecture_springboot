package com.yx.entity;

import java.util.Date;

import lombok.Data;

@Data
public class GetTkxxByTkbhEntity {

	// ID
	private String id;
	
	// 题库编号
	private String tkbh;
	
	// 工种
	private String gz;
	
	// 设定日期
	private Date sdrq;
	
	// 题库类别
	private String tklb;
	
	// 培训层次
	private String pxcc;
	
	// 培训类别
	private String pxlb;
	
	// 适用类型
	private String sylx;
	
	// 单位组织Id
	private String orgId;

}
