package com.yx.dto.examination;

import lombok.Data;

@Data
public class GetQuestionBankInDto {

	// 考试类型
	private String examinationType;
	
	// 培训类型
	private String trainingType;
	
	// 培训层次
	private String trainingLevel;
	
	// 专业内容
	private String workType;
	
	// 学员类型
	private String studentType;
	
	// 学员组织ID
	private String userUnitsId;
}
