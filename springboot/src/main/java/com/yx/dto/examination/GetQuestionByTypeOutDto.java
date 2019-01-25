package com.yx.dto.examination;

import java.util.List;

import com.yx.dto.BaseOutDto;

import lombok.Data;

@Data
public class GetQuestionByTypeOutDto extends BaseOutDto{
	
	// 题目所属种类
	private String questionType;
	// 试题信息
	private List<QuestionInfoDto> questionInfoList;
}
