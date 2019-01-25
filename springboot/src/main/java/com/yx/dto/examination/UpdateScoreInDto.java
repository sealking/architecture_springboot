package com.yx.dto.examination;

import lombok.Data;

@Data
public class UpdateScoreInDto {
	// 学员编号
	private String stuNo;
	
	// 考试编号
	private String examNo;
	
	// 分数
	private double score;
}
