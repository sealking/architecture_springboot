package com.yx.entity;

import lombok.Data;

@Data
public class GetTrainByNoEntity {
	// id
	private String id;
		
	// 培训编号
	private String pxbh;
		
	// 培训名称
	private String pxmc;
	
	// 培训类别
	private String pxlb;
		
	// 培训层次
	private String pxcc;
}
