package com.yx.dto.examination;

import com.yx.dto.BaseOutDto;

import lombok.Data;

@Data
public class GetRankingOutDto extends BaseOutDto {

	// 学员编号
	private String studentNo;
	
	// 学员姓名
	private String studentName;
	
	// 学员成绩
	private float studentScore;
	
	// 考试次数
	private int examCount;
	
	// 试题套数
	private int questionCount;
	
}
