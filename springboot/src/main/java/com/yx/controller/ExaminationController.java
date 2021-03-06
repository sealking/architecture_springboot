package com.yx.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yx.dto.examination.GetExamListByStuNoInDto;
import com.yx.dto.examination.GetExamListByStuNoOutDto;
import com.yx.dto.examination.GetQuestionBankInDto;
import com.yx.dto.examination.GetQuestionBankOutDto;
import com.yx.dto.examination.GetQuestionByTypeInDto;
import com.yx.dto.examination.GetQuestionByTypeOutDto;
import com.yx.dto.examination.GetQuestionInDto;
import com.yx.dto.examination.GetQuestionOutDto;
import com.yx.dto.examination.GetQuestionsByDateInDto;
import com.yx.dto.examination.UpdateIsExamFlagInDto;
import com.yx.dto.examination.UpdateScoreInDto;
import com.yx.dto.examination.GetQuestionsTypeInDto;
import com.yx.dto.examination.GetQuestionsTypeOutDto;
import com.yx.dto.examination.GetRankingInDto;
import com.yx.dto.examination.GetRankingOutDto;
import com.yx.dto.examination.GetTrainByNoInDto;
import com.yx.dto.examination.GetTrainByNoOutDto;
import com.yx.dto.examination.GetTrainByStuNoOutDto;
import com.yx.dto.examination.GetTrainByStuNoForExamOutDto;
import com.yx.service.ExaminationService;

@RestController
@RequestMapping("/examination")
public class ExaminationController {

	@Autowired
	private ExaminationService examinationService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 获取试题信息
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getQuestions")
    public GetQuestionOutDto getQuestions(GetQuestionInDto inDto) {
		GetQuestionOutDto outDto = examinationService.getQuestions(inDto);
		return outDto;
	}
	
	/**
	 * 获取题库种类
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getQuestionsType")
    public GetQuestionsTypeOutDto getQuestionsType(GetQuestionsTypeInDto inDto) {
		GetQuestionsTypeOutDto outDto = examinationService.getQuestionsType(inDto);
		return outDto;
	}
	
	/**
	 * 根据学员编号获取历史模拟考试列表
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getExamListByStuNo")
	public List<GetExamListByStuNoOutDto> getExamListByStuNo(GetExamListByStuNoInDto inDto) {
		List<GetExamListByStuNoOutDto> outDtoList = examinationService.getExamListByStuNo(inDto);
		return outDtoList;
	}	
	
	
	/**
	 * 根据学员编号、题库编号、设定时间获取模拟考试试题内容
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getQuestionsByDate")
	public GetQuestionOutDto getQuestionsByDate(GetQuestionsByDateInDto inDto) {
		GetQuestionOutDto outDto = examinationService.getQuestionsByDate(inDto);
		return outDto;
	}
	
	/**
	 * 根据题目所属类别获取试题信息
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getQuestionsByType")
	public List<GetQuestionByTypeOutDto> getQuestionsByType(GetQuestionByTypeInDto inDto) {
		List<GetQuestionByTypeOutDto> outDtoList = examinationService.getQuestionsByType(inDto);
		return outDtoList;
	}
	
	/**
	 *  根据培训编号获取培训信息
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getTrainByNo")
	public GetTrainByNoOutDto getTrainByNo(GetTrainByNoInDto inDto) {
		GetTrainByNoOutDto outDto = examinationService.getTrainByNo(inDto);
		return outDto;
	}
	
	/**
	 * 更新学员考试成绩
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/updateScore")
	public void updateScore(UpdateScoreInDto inDto) {
		examinationService.updateScore(inDto);
	}
	
	/**
	 * 更新是否考试标识符
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/updateIsExamFlag")
	public void updateIsExamFlag(UpdateIsExamFlagInDto inDto) {
		examinationService.updateIsExamFlag(inDto);
	}
	
	/**
	 * 根据用户编号获取所有的培训信息
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getTrainByStuNo")
	public List<GetTrainByStuNoOutDto> getTrainByStuNo(String stuNo) {
		List<GetTrainByStuNoOutDto> outDtoList = examinationService.getTrainByStuNo(stuNo);
		return outDtoList;
	}
	
	/**
	 * 根据用户编号获取所有的培训信息
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getTrainByStuNoForExam")
	public List<GetTrainByStuNoForExamOutDto> getTrainByStuNoForExam(String stuNo) {
		List<GetTrainByStuNoForExamOutDto> outDtoList = examinationService.getTrainByStuNoForExam(stuNo);
		return outDtoList;
	}
	
	/**
	 * 获取培训学员成绩排名
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getRanking")
	public List<GetRankingOutDto> getRanking(GetRankingInDto inDto) {
		List<GetRankingOutDto> outDtoList = examinationService.getRanking(inDto);
		return outDtoList;
	}
	
	/**
	 * 获取考试题库
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getQuestionBank")
	public List<GetQuestionBankOutDto> getQuestionBank(GetQuestionBankInDto inDto) {
		List<GetQuestionBankOutDto> outDtoList = examinationService.getQuestionBank(inDto);
		return outDtoList;
	}
	
}
