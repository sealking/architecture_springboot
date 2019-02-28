package com.yx.dto.examination;

import java.util.Date;

import lombok.Data;

@Data
public class UpdateScoreInDto {
	// 学员编号
	private String stuNo;
	
	// 考试编号
	private String examNo;
	
	// 分数
	private double score;
	
	// 培训编号
	private String trainNo;
	
	// 模拟考试设定日期
	private String settingDate;
	
	// 模拟在线考试区分(1:在线考试，2:模拟考试)
	private String examinationType;
	
}
