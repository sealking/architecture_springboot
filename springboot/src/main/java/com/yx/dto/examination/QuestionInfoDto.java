package com.yx.dto.examination;

import java.util.List;
import java.util.Map;

import com.yx.dto.BaseOutDto;

import lombok.Data;

@Data
public class QuestionInfoDto extends BaseOutDto{
	
	// 题目编号
	private String questionNo;
	
	// 题目种类
	private String questionSort;
	
	// 题目类型
	private String questionType;
	
	// 题目内容
	private String questionContent;
	
	// 选项内容List
	private List<Map<String,Object>> itemInfoList;
	
	// 正确答案
	private String answer;
	
	// 题目分值
	private float score;
}
