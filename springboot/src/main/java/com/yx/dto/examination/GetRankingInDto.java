package com.yx.dto.examination;

import lombok.Data;

@Data
public class GetRankingInDto {
	// 学员编号
	private String studentNo;
		
	// 培训编号
	private String trainNo;
	
	// 考试类型 1：在线考试，2：模拟考试
	private String examinationType;
}
