package com.yx.service;

import java.util.List;

import com.yx.dto.examination.GetExamListByStuNoInDto;
import com.yx.dto.examination.GetExamListByStuNoOutDto;
import com.yx.dto.examination.GetQuestionByTypeInDto;
import com.yx.dto.examination.GetQuestionByTypeOutDto;
import com.yx.dto.examination.GetQuestionInDto;
import com.yx.dto.examination.GetQuestionOutDto;
import com.yx.dto.examination.GetQuestionsByDateInDto;
import com.yx.dto.examination.GetQuestionsTypeInDto;
import com.yx.dto.examination.GetQuestionsTypeOutDto;
import com.yx.dto.examination.GetTrainByNoInDto;
import com.yx.dto.examination.GetTrainByNoOutDto;
import com.yx.dto.examination.UpdateIsExamFlagInDto;
import com.yx.dto.examination.UpdateScoreInDto;
import com.yx.entity.GetTrainByNoEntity;
import com.yx.entity.UpdateIsExamFlagParm;
import com.yx.entity.UpdateScoreParm;

public interface ExaminationService {
	
	// 获取试题信息
	public GetQuestionOutDto getQuestions(GetQuestionInDto inDto);
	
	// 获取题库种类
	public GetQuestionsTypeOutDto getQuestionsType(GetQuestionsTypeInDto inDto);
	
	// 根据学员编号获取历史模拟考试列表
	public List<GetExamListByStuNoOutDto> getExamListByStuNo(GetExamListByStuNoInDto inDto);
	
	// 根据学员编号、题库编号、设定时间获取模拟考试试题内容
	public GetQuestionOutDto getQuestionsByDate(GetQuestionsByDateInDto inDto);
	
	// 根据题目所属类别获取试题信息
	public List<GetQuestionByTypeOutDto> getQuestionsByType(GetQuestionByTypeInDto inDto);
	
	// 根据培训编号获取培训信息
	public GetTrainByNoOutDto getTrainByNo(GetTrainByNoInDto inDto);
	
	// 更新学员考试成绩
	public void updateScore(UpdateScoreInDto inDto);
	
	// 更新是否考试标识符
	public void updateIsExamFlag(UpdateIsExamFlagInDto inDto);
	
}
