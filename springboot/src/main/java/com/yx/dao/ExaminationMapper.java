package com.yx.dao;

import com.yx.dto.examination.GetQuestionBankInDto;
import com.yx.dto.examination.GetQuestionBankOutDto;
import com.yx.entity.GetExamListByStuNoEntity;
import com.yx.entity.GetExamListByStuNoParm;
import com.yx.entity.GetQuestionBankEntity;
import com.yx.entity.GetQuestionBankParm;
import com.yx.entity.GetQuestionsSettingEntity;
import com.yx.entity.GetQuestionsSettingParm;
import com.yx.entity.GetQuestionsTypeEntity;
import com.yx.entity.GetQuestionsTypeParm;
import com.yx.entity.GetRandomQuestionParm;
import com.yx.entity.GetRankingEntity;
import com.yx.entity.GetRankingParm;
import com.yx.entity.GetTkxxByTkbhEntity;
import com.yx.entity.GetTkxxByTkbhParm;
import com.yx.entity.GetTmxxEntity;
import com.yx.entity.GetTmxxParm;
import com.yx.entity.GetTrainByNoEntity;
import com.yx.entity.GetTrainByStuNoEntity;
import com.yx.entity.InsertQuestionsHistoryParm;
import com.yx.entity.UpdateIsExamFlagParm;
import com.yx.entity.InsertMockScoreParm;
import com.yx.entity.UpdateScoreParm;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExaminationMapper {
	// 根据学员编号检索在线考试试题
	public List<GetTmxxEntity> getTmxxForZxksByStudentNo(GetTmxxParm parm);
		
	// 根据学员编号检索模拟考试试题
	public List<GetTmxxEntity> getTmxxForMnksByStudentNo(GetTmxxParm parm);

	// 获取考试时长
	public HashMap<String, String> getExaminationTimeByStudentNo(GetTmxxParm parm);
	
	// 获取题库种类
	public List<GetQuestionsTypeEntity> getQuestionsType(GetQuestionsTypeParm parm);
	
	// 获取指定工种的题库设定
	public List<GetQuestionsSettingEntity> getQuestionsSetting(GetQuestionsSettingParm parm);
	
	// 随机获取模拟试题
	public List<GetTmxxEntity> getRandomQuestion(GetRandomQuestionParm parm);
	
	// 将获取的模拟试题保存到题库历史表中
	public int insertQuestionsHistory(List<InsertQuestionsHistoryParm> parm);
	
	// 根据学员编号获取历史模拟考试列表
	public List<GetExamListByStuNoEntity> getExamListByStuNo(GetExamListByStuNoParm parm);
	
	// 根据学员编号、题库编号、设定时间获取模拟考试试题内容
	public List<GetTmxxEntity> getQuestionsByDate(GetTmxxParm pram);
	
	// 根据题目所属类别获取试题信息
	public List<GetTmxxEntity> getQuestionsByType(GetTmxxParm pram);
	
	// 根据培训编号获取培训信息
	public GetTrainByNoEntity getTrainByNo(String pxbh);
	
	// 更新学员考试成绩
	public int updateScore(UpdateScoreParm parm);
	
	// 更新是否考试标识符
	public int updateIsExamFlag(UpdateIsExamFlagParm parm);
	
	// 根据考试编号获取工种岗位
	public String getWorkTypeByExamNo(String examNo);
	
	// 插入模拟考试成绩表
	public int insertMockScore(InsertMockScoreParm parm);
	
	// 根据用户编号获取所有的培训信息
	public List<GetTrainByStuNoEntity> getTrainByStuNo(String xybh);
	
	// 获取培训学员在线考试成绩排名
	public List<GetRankingEntity> getRankingForOnline(GetRankingParm parm);
	
	// 获取培训学员模拟考试成绩排名
	public List<GetRankingEntity> getRankingForMock(GetRankingParm parm);
	
	// 获取考试题库
	public List<GetQuestionBankEntity> getQuestionBank(GetQuestionBankParm parm);
	
	// 获取题库编号获取题库信息
	public GetTkxxByTkbhEntity getTkxxByTkbh(GetTkxxByTkbhParm parm);
	
	// 根据题库编号获取固定出题的试题信息
	public List<GetTmxxEntity> getTmxxForZxksByTkbh(String tkbh);
	
	// 根据组织Id获取所对应的所有父组织
	public String getParentIdById(String childId);
	
}