package com.yx.dto.examination;

import com.yx.dto.BaseOutDto;
import lombok.Data;

@Data
public class GetExamListByStuNoOutDto extends BaseOutDto{

	// 学员编号
	private String studentNo;
	
	// 题库编号
	private String questionsNo;
	
	// 培训编号
	private String trainNo;
	
	// 设定时间
	private String settingDate;
}
