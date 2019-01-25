package com.yx.dto.examination;

import lombok.Data;

@Data
public class UpdateIsExamFlagInDto {
	// 学员编号
	private String stuNo;
	
	// 考试编号
	private String examNo;
}
