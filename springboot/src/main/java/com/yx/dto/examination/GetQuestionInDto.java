package com.yx.dto.examination;

import lombok.Data;

@Data
public class GetQuestionInDto {
	
	// 学员编号
	private String studentNo;
	
	// 考试类型1：在线考试，2：模拟考试
	private String examinationType;
	
	// 试题类型1：在线试题，2：离线试题
	private String questionType;
	
	// 工种
	private String workType;
	
	// 题库编号
	private String questionBankId;
}
