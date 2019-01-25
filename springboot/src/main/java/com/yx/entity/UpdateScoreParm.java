package com.yx.entity;

import lombok.Data;

@Data
public class UpdateScoreParm {
	// 学员编号
	private String xybh;
	
	// 考试编号
	private String ksbh;
	
	// 分数
	private double kscj;
}
