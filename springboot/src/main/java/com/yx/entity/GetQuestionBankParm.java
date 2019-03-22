package com.yx.entity;

import java.util.List;

import lombok.Data;

@Data
public class GetQuestionBankParm {

	// 考试类型
	private String tklb;
		
	// 培训类型
	private String pxlb;
		
	// 培训层次
	private String pxcc;
		
	// 专业内容
	private String gz;
		
	// 学员类型
	private String sylx;
		
	// 学员组织ID
	private List<String> orgIdList;
}
