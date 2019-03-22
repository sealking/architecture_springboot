package com.yx.dto.examination;

import lombok.Data;

@Data
public class GetExamListByStuNoInDto {

	// 学员编号
	private String studentNo;
	
	// 工种
	private String workType;
	
	// 题库编号
	private String questionBank;
}
