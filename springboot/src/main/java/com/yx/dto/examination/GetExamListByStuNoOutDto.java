package com.yx.dto.examination;

import java.util.Date;

import com.yx.dto.BaseOutDto;

import lombok.Data;

@Data
public class GetExamListByStuNoOutDto extends BaseOutDto{

	// 学员编号
	private String studentNo;
	
	// 题库编号
	private String questionsNo;
	
	// 设定时间
	private String settingDate;
}
