package com.yx.dto.examination;

import java.util.Date;

import lombok.Data;

@Data
public class GetQuestionInDto {
	
	// 学员编号
	private String studentNo;
	
	// 考试类型 0：模拟考试；1：正式考试
	private String examinationType;
	
	// 试题类型1：在线试题，2：离线试题
	private String questionType;
	
	// 工种
	private String workType;
}
