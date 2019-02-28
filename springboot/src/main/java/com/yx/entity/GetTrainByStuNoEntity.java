package com.yx.entity;

import lombok.Data;

@Data
public class GetTrainByStuNoEntity {

	// 主键
	private String id;
	
	// 培训编号
	private String pxbh;
	
	// 培训名称
	private String pxmc;
}
