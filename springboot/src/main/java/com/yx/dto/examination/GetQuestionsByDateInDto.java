package com.yx.dto.examination;

import lombok.Data;

@Data
public class GetQuestionsByDateInDto {

	// 培训编号
	private String trainNo;
	
	// 学员编号
	private String studentNo;
	
	// 题库编号
	private String questionsNo;
	
	// 设定时间
	private String settingDate;
	
}
