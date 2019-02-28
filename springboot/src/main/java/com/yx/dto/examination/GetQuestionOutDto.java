package com.yx.dto.examination;

import java.util.List;
import com.yx.dto.BaseOutDto;
import lombok.Data;

@Data
public class GetQuestionOutDto extends BaseOutDto{
	
	// 试题信息
	private List<QuestionInfoDto> questionInfoList;
	
	// 考试时间分钟
	private int examinationMinute;
	
	// 考试编号
	private String examinationNo;
	
	// 培训编号
	private String trainNo;
	
	// 学员是否参加考试Flag
	private String isExamFlag;
	
	// 工种岗位
	private String workType;
	
	// 培训类别
	private String trainType;
	
	// 培训层次
	private String trainLevel;
	
	// 模拟考试设定日期
	private String settingDate;
}
