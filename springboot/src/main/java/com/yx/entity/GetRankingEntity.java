package com.yx.entity;

import lombok.Data;

@Data
public class GetRankingEntity {
	
	// 学员编号
	private String xybh;
	
	// 学员姓名
	private String xyxm;
	
	// 培训编号
	private String pxbh;
	
	// 随机编号
	private String sjbh;
	
	// 考试成绩
	private float kscj;
	
	// 考试次数
	private int kscs;
	
	// 考试套数
	private int ksts;

}
