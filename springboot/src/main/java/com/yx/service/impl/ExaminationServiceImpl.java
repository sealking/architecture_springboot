package com.yx.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.yx.dao.ExaminationMapper;
import com.yx.dto.common.DataTypeInfoByDetailCodeInDto;
import com.yx.dto.common.DataTypeInfoByDetailCodeOutDto;
import com.yx.dto.common.DataTypeInfoDto;
import com.yx.dto.common.DataTypeInfoInDto;
import com.yx.dto.common.DataTypeInfoOutDto;
import com.yx.dto.examination.GetExamListByStuNoInDto;
import com.yx.dto.examination.GetExamListByStuNoOutDto;
import com.yx.dto.examination.GetQuestionByTypeInDto;
import com.yx.dto.examination.GetQuestionByTypeOutDto;
import com.yx.dto.examination.GetQuestionInDto;
import com.yx.dto.examination.GetQuestionOutDto;
import com.yx.dto.examination.GetQuestionsByDateInDto;
import com.yx.dto.examination.GetQuestionsTypeInDto;
import com.yx.dto.examination.GetQuestionsTypeOutDto;
import com.yx.dto.examination.GetRankingInDto;
import com.yx.dto.examination.GetRankingOutDto;
import com.yx.dto.examination.GetTrainByNoInDto;
import com.yx.dto.examination.GetTrainByNoOutDto;
import com.yx.dto.examination.GetTrainByStuNoOutDto;
import com.yx.dto.examination.QuestionInfoDto;
import com.yx.dto.examination.UpdateIsExamFlagInDto;
import com.yx.dto.examination.UpdateScoreInDto;
import com.yx.entity.GetExamListByStuNoEntity;
import com.yx.entity.GetExamListByStuNoParm;
import com.yx.entity.GetQuestionsSettingEntity;
import com.yx.entity.GetQuestionsSettingParm;
import com.yx.entity.GetQuestionsTypeEntity;
import com.yx.entity.GetQuestionsTypeParm;
import com.yx.entity.GetRandomQuestionParm;
import com.yx.entity.GetRankingEntity;
import com.yx.entity.GetRankingParm;
import com.yx.entity.GetTmxxEntity;
import com.yx.entity.GetTmxxParm;
import com.yx.entity.GetTrainByNoEntity;
import com.yx.entity.GetTrainByStuNoEntity;
import com.yx.entity.InsertQuestionsHistoryParm;
import com.yx.entity.UpdateIsExamFlagParm;
import com.yx.entity.InsertMockScoreParm;
import com.yx.entity.UpdateScoreParm;
import com.yx.service.CommonService;
import com.yx.service.ExaminationService;

@Service
public class ExaminationServiceImpl implements ExaminationService {

	@Autowired
	private ExaminationMapper examinationMapper;

	@Autowired
	private CommonService commonService;

	/**
	 * 获取试题信息
	 * 
	 * @param inDto
	 * @return
	 */
	@Override
	@Transactional
	public GetQuestionOutDto getQuestions(GetQuestionInDto inDto) {
		GetQuestionOutDto outDto = new GetQuestionOutDto();
		// 设置学员编号
		GetTmxxParm getTmxxParm = new GetTmxxParm();
		getTmxxParm.setStudentNo(inDto.getStudentNo());

		List<GetTmxxEntity> tmxxEntityList = new ArrayList<GetTmxxEntity>();

		// 1：在线考试，2：模拟考试
		if ("1".equals(inDto.getExaminationType())) {
			// 试题信息
			tmxxEntityList = examinationMapper.getTmxxForZxksByStudentNo(getTmxxParm);
			if (tmxxEntityList.size() == 0) {
				outDto.setReturnCode("1");
				outDto.setMsg("试题不存在，请与管理员联系！");
				return outDto;
			} else {
				// 考试时长
				HashMap<String, String> examinationTime = examinationMapper.getExaminationTimeByStudentNo(getTmxxParm);
				String beginTime = examinationTime.get("kssj");
				String endTime = examinationTime.get("jssj");

				if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
					outDto.setReturnCode("1");
					outDto.setMsg("考试时间未设置，请与管理员联系！");
					return outDto;
				} else {
					SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					beginTime = "2019-01-01 " + beginTime;
					endTime = "2019-01-01 " + endTime;

					try {
						Date fromDate1 = simpleFormat.parse(beginTime);
						Date toDate1 = simpleFormat.parse(endTime);
						long from = fromDate1.getTime();
						long to = toDate1.getTime();
						int examinationMinute = (int) ((to - from) / (1000 * 60));
						// 考试时长
						outDto.setExaminationMinute(examinationMinute);
						// 考试编号
						outDto.setExaminationNo(examinationTime.get("id"));
						// 培训编号
						outDto.setTrainNo(examinationTime.get("pxbh"));
						// 学员是否考过Flag
						outDto.setIsExamFlag(examinationTime.get("isExamFlag"));
						
						// 获取该考试对应的工种
						String workType = examinationMapper.getWorkTypeByExamNo(examinationTime.get("id"));
						outDto.setWorkType(workType);
						
						// 获取培训类别，培训层次
						GetTrainByNoInDto getTrainByNoInDto = new GetTrainByNoInDto();
						getTrainByNoInDto.setTrainNo(examinationTime.get("pxbh"));
						GetTrainByNoOutDto getTrainByNoOutDto = this.getTrainByNo(getTrainByNoInDto);
						outDto.setTrainType(getTrainByNoOutDto.getType());
						outDto.setTrainLevel(getTrainByNoOutDto.getLevel());
						
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			GetQuestionsSettingParm settingParm = new GetQuestionsSettingParm();
			settingParm.setWorkTypeCode(inDto.getWorkType());
			List<GetQuestionsSettingEntity> settingEntityList = examinationMapper.getQuestionsSetting(settingParm);

			for (GetQuestionsSettingEntity settingEntity : settingEntityList) {
				GetRandomQuestionParm randomQuestionParm = new GetRandomQuestionParm();
				List<GetTmxxEntity> randomQuestionList = new ArrayList<GetTmxxEntity>();
				randomQuestionParm.setTmlx(settingEntity.getTmlx());
				randomQuestionParm.setTmzl(settingEntity.getTmzl());
				randomQuestionParm.setTmnyd(settingEntity.getTmnyd());
				randomQuestionParm.setTmsl(settingEntity.getTmsl());

				randomQuestionList = examinationMapper.getRandomQuestion(randomQuestionParm);
				tmxxEntityList.addAll(randomQuestionList);
			}

			if (tmxxEntityList.size() > 0) {
				List<InsertQuestionsHistoryParm> historyList = new ArrayList<InsertQuestionsHistoryParm>();

				Date sdrq = new Date();
				for (GetTmxxEntity tmxxEntity : tmxxEntityList) {
					InsertQuestionsHistoryParm historyParm = new InsertQuestionsHistoryParm();
					historyParm.setTkbh(settingEntityList.get(0).getTkbh());
					historyParm.setTmbh(tmxxEntity.getTmbh());
					historyParm.setXybh(inDto.getStudentNo());
					historyParm.setKsbh("");
					historyParm.setSdrq(sdrq);
					historyList.add(historyParm);
				}
				examinationMapper.insertQuestionsHistory(historyList);
				
				SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				outDto.setSettingDate(simpleFormat.format(sdrq));
			}
			// 培训编号
			HashMap<String, String> ExaminationTime = examinationMapper.getExaminationTimeByStudentNo(getTmxxParm);
			outDto.setTrainNo(ExaminationTime.get("pxbh"));
			// 考试时间
			outDto.setExaminationMinute(90);
		}

		// 获取试题失败
		if (tmxxEntityList.size() == 0) {
			outDto.setReturnCode("1");
			outDto.setMsg("试题不存在，请与管理员联系！");
			return outDto;
		} else {
			List<QuestionInfoDto> questionInfoList = this.getQuestionsInfo(tmxxEntityList);
			outDto.setQuestionInfoList(questionInfoList);
			outDto.setReturnCode("0");
			outDto.setMsg("试题获取成功");
		}

		return outDto;
	}

	/**
	 * 获取题库种类
	 * 
	 * @param inDto
	 * @return
	 */
	@Override
	public GetQuestionsTypeOutDto getQuestionsType(GetQuestionsTypeInDto inDto) {

		// 获取题库种类信息
		GetQuestionsTypeParm parm = new GetQuestionsTypeParm();
		List<GetQuestionsTypeEntity> entityList = new ArrayList<GetQuestionsTypeEntity>();
		parm.setWorkTypeCode(inDto.getWorkTypeCode());
		entityList = examinationMapper.getQuestionsType(parm);

		// 拼接题库种类字符串
		String resultStr = "";
		for (int i = 0; i < entityList.size(); i++) {
			if (i == 0) {
				resultStr += "（";
			}
			DataTypeInfoByDetailCodeInDto dataTypeInfoInDto = new DataTypeInfoByDetailCodeInDto();
			dataTypeInfoInDto.setTypeCode("tmzl");
			dataTypeInfoInDto.setTypeDetailCode(entityList.get(i).getTmzl());
			DataTypeInfoByDetailCodeOutDto dataTypeInfoOutDto = commonService
					.getDataTypeInfoByDetailCode(dataTypeInfoInDto);
			String questionsTypeStr = dataTypeInfoOutDto.getTypeDetailName();
			resultStr += questionsTypeStr + "：" + entityList.get(i).getTmsl() + "题";
			if (i == entityList.size() - 1) {
				resultStr += "）";
			} else {
				resultStr += "，";
			}
		}
		GetQuestionsTypeOutDto outDto = new GetQuestionsTypeOutDto();
		outDto.setQuestionsType(resultStr);
		return outDto;
	}

	/**
	 * 根据学员编号获取历史模拟考试列表
	 * 
	 * @param inDto
	 * @return
	 */
	@Override
	public List<GetExamListByStuNoOutDto> getExamListByStuNo(GetExamListByStuNoInDto inDto) {
		List<GetExamListByStuNoOutDto> outList = new ArrayList<GetExamListByStuNoOutDto>();
		GetExamListByStuNoParm parm = new GetExamListByStuNoParm();
		List<GetExamListByStuNoEntity> entityList = new ArrayList<GetExamListByStuNoEntity>();
		parm.setXybh(inDto.getStudentNo());
		parm.setGz(inDto.getWorkType());
		entityList = examinationMapper.getExamListByStuNo(parm);

		for (GetExamListByStuNoEntity entity : entityList) {
			GetExamListByStuNoOutDto outDto = new GetExamListByStuNoOutDto();
			outDto.setStudentNo(entity.getXybh());
			outDto.setQuestionsNo(entity.getTkbh());
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			outDto.setSettingDate(simpleFormat.format(entity.getSdrq()));
			outList.add(outDto);
		}
		return outList;
	}

	/**
	 * 根据学员编号、题库编号、设定时间获取模拟考试试题内容
	 * 
	 * @param inDto
	 * @return
	 */
	@Override
	public GetQuestionOutDto getQuestionsByDate(GetQuestionsByDateInDto inDto) {
		GetQuestionOutDto outDto = new GetQuestionOutDto();
		List<QuestionInfoDto> list = new ArrayList<QuestionInfoDto>();
		GetTmxxParm getTmxxParm = new GetTmxxParm();

		getTmxxParm.setStudentNo(inDto.getStudentNo());
		getTmxxParm.setQuestionsNo(inDto.getQuestionsNo());
		getTmxxParm.setSettingDate(inDto.getSettingDate());

		List<GetTmxxEntity> tmxxEntityList = new ArrayList<GetTmxxEntity>();
		tmxxEntityList = examinationMapper.getQuestionsByDate(getTmxxParm);
		if (tmxxEntityList.size() == 0) {
			outDto.setReturnCode("1");
			outDto.setMsg("试题不存在，请与管理员联系！");
			return outDto;
		} else {
			outDto.setExaminationMinute(90);
			list = this.getQuestionsInfo(tmxxEntityList);
			outDto.setQuestionInfoList(list);
			
			// 培训编号
			HashMap<String, String> ExaminationTime = examinationMapper.getExaminationTimeByStudentNo(getTmxxParm);
			outDto.setTrainNo(ExaminationTime.get("pxbh"));
			
			outDto.setReturnCode("0");
			outDto.setMsg("试题获取成功");
		}
		return outDto;
	}
	
	/**
	 * 根据题目所属类别获取试题信息
	 * 
	 * @param inDto
	 * @return
	 */
	@Override
	public List<GetQuestionByTypeOutDto> getQuestionsByType(GetQuestionByTypeInDto inDto) {
		List<GetQuestionByTypeOutDto> outDtoList = new ArrayList<GetQuestionByTypeOutDto>();

		// 数据字典中获取【题目所属种类】
		DataTypeInfoInDto dataTypeInfoInDto = new DataTypeInfoInDto();
		dataTypeInfoInDto.setTypeCode("tmzl");
		DataTypeInfoOutDto dataTypeInfoOutDto = commonService.getDataTypeInfo(dataTypeInfoInDto);

		// 根据数据字典的code获取题目列表
		if (dataTypeInfoOutDto.getDataTypeList().size() > 0) {
			for (DataTypeInfoDto dataTypeInfoDto : dataTypeInfoOutDto.getDataTypeList()) {
				List<QuestionInfoDto> list = new ArrayList<QuestionInfoDto>();
				GetQuestionByTypeOutDto getQuestionByTypeOutDto = new GetQuestionByTypeOutDto();
				getQuestionByTypeOutDto.setQuestionTypeKey(dataTypeInfoDto.getKey());
				getQuestionByTypeOutDto.setQuestionTypeValue(dataTypeInfoDto.getValue());

				GetTmxxParm getTmxxParm = new GetTmxxParm();
				List<GetTmxxEntity> tmxxEntityList = new ArrayList<GetTmxxEntity>();
				getTmxxParm.setQuestionType(dataTypeInfoDto.getKey());
				tmxxEntityList = examinationMapper.getQuestionsByType(getTmxxParm);
				list = this.getQuestionsInfo(tmxxEntityList);
				getQuestionByTypeOutDto.setQuestionInfoList(list);
				outDtoList.add(getQuestionByTypeOutDto);
			}
		}
		return outDtoList;
	}
	
	/**
	 *  根据培训编号获取培训信息
	 * 
	 * @param inDto
	 * @return
	 */
	@Override
	public GetTrainByNoOutDto getTrainByNo(GetTrainByNoInDto inDto) {
		GetTrainByNoOutDto outDto = new GetTrainByNoOutDto();
		GetTrainByNoEntity getTrainByNoEntity = examinationMapper.getTrainByNo(inDto.getTrainNo());
		
		if(ObjectUtils.isEmpty(getTrainByNoEntity)) {
			outDto.setReturnCode("1");
			outDto.setMsg("培训信息获取失败，请联系管理员");
		} else {
			outDto.setReturnCode("0");
			outDto.setId(getTrainByNoEntity.getId());
			outDto.setNo(getTrainByNoEntity.getPxbh());
			outDto.setName(getTrainByNoEntity.getPxmc());
			outDto.setType(getTrainByNoEntity.getPxlb());
			outDto.setLevel(getTrainByNoEntity.getPxcc());
		}
		return outDto;
	}
	
	/**
	 *  更新学员考试成绩
	 * 
	 * @param inDto
	 * @return
	 */
	@Override
	@Transactional
	public void updateScore(UpdateScoreInDto inDto) {
		if("1".equals(inDto.getExaminationType())) {
			UpdateScoreParm parm = new UpdateScoreParm();
			parm.setXybh(inDto.getStuNo());
			parm.setKsbh(inDto.getExamNo());
			parm.setKscj(inDto.getScore());
			
			examinationMapper.updateScore(parm);
		} else {
			InsertMockScoreParm parm = new InsertMockScoreParm();
			parm.setXybh(inDto.getStuNo());
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			try {
				parm.setSdrq(simpleFormat.parse(inDto.getSettingDate()));
				parm.setKscj(inDto.getScore());
				parm.setPxbh(inDto.getTrainNo());
				examinationMapper.insertMockScore(parm);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 *  更新是否考试标识符
	 * 
	 * @param inDto
	 * @return
	 */
	@Override
	@Transactional
	public void updateIsExamFlag(UpdateIsExamFlagInDto inDto) {
		UpdateIsExamFlagParm parm = new UpdateIsExamFlagParm();
		parm.setXybh(inDto.getStuNo());
		parm.setKsbh(inDto.getExamNo());
		
		examinationMapper.updateIsExamFlag(parm);
	}
	
	/**
	 *  获取培训学员成绩排名
	 * 
	 * @param inDto
	 * @return
	 */
	@Override
	public List<GetRankingOutDto> getRanking(GetRankingInDto inDto) {
		String examinationType = inDto.getExaminationType();
		GetRankingParm getRankingParm = new GetRankingParm();
		getRankingParm.setStudentNo(inDto.getStudentNo());
		getRankingParm.setTrainNo(inDto.getTrainNo());
		List<GetRankingOutDto> outDtoList = new ArrayList<GetRankingOutDto>();
		// 1：在线考试，2：模拟考试
		if("1".equals(examinationType)) {
			List<GetRankingEntity> entityList = examinationMapper.getRankingForOnline(getRankingParm);
			for(GetRankingEntity entity : entityList) {
				GetRankingOutDto outDto = new GetRankingOutDto();
				outDto.setStudentNo(entity.getXybh());
				outDto.setStudentName(entity.getXyxm());
				outDto.setStudentScore(entity.getKscj());
				outDtoList.add(outDto);
			}
		} else {
			List<GetRankingEntity> entityList = examinationMapper.getRankingForMock(getRankingParm);
			for(GetRankingEntity entity : entityList) {
				GetRankingOutDto outDto = new GetRankingOutDto();
				outDto.setStudentNo(entity.getXybh());
				outDto.setStudentName(entity.getXyxm());
				outDto.setStudentScore(entity.getKscj());
				outDto.setExamCount(entity.getKscs());
				outDto.setQuestionCount(entity.getKsts());
				outDtoList.add(outDto);
			}
		}
		
		 return outDtoList;
	}
	
	/**
	 *  根据用户编号获取所有的培训信息
	 * 
	 * @param stuNo
	 * @return
	 */
	@Override
	public List<GetTrainByStuNoOutDto> getTrainByStuNo(String stuNo) {
		List<GetTrainByStuNoOutDto> outDtoList = new ArrayList<GetTrainByStuNoOutDto>();
		List<GetTrainByStuNoEntity> getTrainByStuNoEntityList = examinationMapper.getTrainByStuNo(stuNo);
		for(GetTrainByStuNoEntity entity : getTrainByStuNoEntityList) {
			GetTrainByStuNoOutDto outDto = new GetTrainByStuNoOutDto();
			outDto.setId(entity.getId());
			outDto.setNo(entity.getPxbh());
			outDto.setName(entity.getPxmc());
			outDtoList.add(outDto);
		}
		return outDtoList;
	}

	/**
	 * 封装试题信息
	 * 
	 * @param tmxxEntityList
	 * @return
	 */
	private List<QuestionInfoDto> getQuestionsInfo(List<GetTmxxEntity> tmxxEntityList) {
		// 封装试题信息
		List<QuestionInfoDto> questionInfoList = new ArrayList<QuestionInfoDto>();
		for (GetTmxxEntity tmxxEntity : tmxxEntityList) {
			QuestionInfoDto questionInfoDto = new QuestionInfoDto();

			// 题目编号
			questionInfoDto.setQuestionNo(tmxxEntity.getTmbh());

			// 题目种类
			questionInfoDto.setQuestionSort(tmxxEntity.getTmzl());

			// 题目类型
			questionInfoDto.setQuestionType(tmxxEntity.getTmlx());

			// 题目内容
			questionInfoDto.setQuestionContent(tmxxEntity.getTmnr());

			if ("1".equals(tmxxEntity.getTmlx()) || "2".equals(tmxxEntity.getTmlx())) {
				// 选项内容List
				String itemInfo = tmxxEntity.getTmxx();
				List<Map<String, Object>> itemInfoList = this.getItemInfo(itemInfo);
				questionInfoDto.setItemInfoList(itemInfoList);
			}
			
			if ("3". equals(tmxxEntity.getTmlx())) {
				List<Map<String, Object>> itemInfoList = new ArrayList<Map<String, Object>>();
				Map<String, Object> okMap = new HashMap<String, Object>();
				okMap.put("value", "A");
				okMap.put("label", "正确");
				okMap.put("disabled", false);
				Map<String, Object> errorMap = new HashMap<String, Object>();
				errorMap.put("value", "B");
				errorMap.put("label", "错误");
				errorMap.put("disabled", false);
				itemInfoList.add(okMap);
				itemInfoList.add(errorMap);
				
				questionInfoDto.setItemInfoList(itemInfoList);
			}

			// 正确答案
			questionInfoDto.setAnswer(tmxxEntity.getTmda());

			// 题目分值
			questionInfoDto.setScore(Float.parseFloat(tmxxEntity.getTmfz()));

			questionInfoList.add(questionInfoDto);
		}

		return questionInfoList;
	}
	
	/**
	 * 分隔题目选项（分隔符为【字母| 、】）
	 * 
	 * @param itemInfo
	 * @return
	 */
	private List<Map<String,Object>> getItemInfo(String itemInfo) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();

		char ch = 'A';
		int charInt = ch;

		for (int i = charInt + 1; i < charInt + 26; i++) {
			Map<String, Object> option = new HashMap<String, Object>();
			char temp = (char) i;
			String strTemp = "|" + temp + "、";
			int itemaEndIndex = itemInfo.indexOf(strTemp);
			if (itemaEndIndex == -1) {
				String optionValue = String.valueOf((char) (i-1));
				String optionLabel = itemInfo;
				option.put("value", optionValue);
				option.put("label", optionLabel);
				option.put("disabled", false);
				resultList.add(option);
				return resultList;
			} else {
				String optionValue = String.valueOf((char) (i-1));
				String optionLabel = itemInfo.substring(0, itemaEndIndex);
				option.put("value", optionValue);
				option.put("label", optionLabel);
				option.put("disabled", false);
				resultList.add(option);
				itemInfo = itemInfo.substring(itemaEndIndex + 1);
			}
		}
		return resultList;
	}

}
