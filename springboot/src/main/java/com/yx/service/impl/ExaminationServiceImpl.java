package com.yx.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.yx.dto.examination.GetTrainByNoInDto;
import com.yx.dto.examination.GetTrainByNoOutDto;
import com.yx.dto.examination.QuestionInfoDto;
import com.yx.dto.examination.UpdateIsExamFlagInDto;
import com.yx.dto.examination.UpdateScoreInDto;
import com.yx.entity.GetDataTypeInfoByDetailCodeParm;
import com.yx.entity.GetExamListByStuNoEntity;
import com.yx.entity.GetExamListByStuNoParm;
import com.yx.entity.GetQuestionsSettingEntity;
import com.yx.entity.GetQuestionsSettingParm;
import com.yx.entity.GetQuestionsTypeEntity;
import com.yx.entity.GetQuestionsTypeParm;
import com.yx.entity.GetRandomQuestionParm;
import com.yx.entity.GetTmxxEntity;
import com.yx.entity.GetTmxxParm;
import com.yx.entity.GetTrainByNoEntity;
import com.yx.entity.InsertQuestionsHistoryParm;
import com.yx.entity.UpdateIsExamFlagParm;
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
				HashMap<String, String> ExaminationTime = examinationMapper.getExaminationTimeByStudentNo(getTmxxParm);
				String beginTime = ExaminationTime.get("kssj");
				String endTime = ExaminationTime.get("jssj");

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
						outDto.setExaminationNo(ExaminationTime.get("id"));
						// 培训编号
						outDto.setTrainNo(ExaminationTime.get("pxbh"));
						// 学员是否考过Flag
						outDto.setIsExamFlag(ExaminationTime.get("isExamFlag"));
						
						// 获取该考试对应的工种
						String workType = examinationMapper.getWorkTypeByExamNo(ExaminationTime.get("id"));
						outDto.setWorkType(workType);
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
			}
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
				getQuestionByTypeOutDto.setQuestionType(dataTypeInfoDto.getTypeDetailCode());

				GetTmxxParm getTmxxParm = new GetTmxxParm();
				List<GetTmxxEntity> tmxxEntityList = new ArrayList<GetTmxxEntity>();
				getTmxxParm.setQuestionType(dataTypeInfoDto.getTypeDetailCode());
				tmxxEntityList = examinationMapper.getQuestionsByType(getTmxxParm);
				list = this.getQuestionsInfo(tmxxEntityList);
				getQuestionByTypeOutDto.setQuestionType(dataTypeInfoDto.getTypeDetailCode());
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
		UpdateScoreParm parm = new UpdateScoreParm();
		parm.setXybh(inDto.getStuNo());
		parm.setKsbh(inDto.getExamNo());
		parm.setKscj(inDto.getScore());
		
		examinationMapper.updateScore(parm);
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
				List<String> itemInfoList = this.getItemInfo(itemInfo);
				questionInfoDto.setItemInfoList(itemInfoList);

				// 选项List
				char ch = 'A';
				int charInt = ch;
				List<String> itemList = new ArrayList<String>();
				for (int i = 0; i < itemInfoList.size(); i++) {
					itemList.add(String.valueOf((char) charInt++));
				}
				questionInfoDto.setItemList(itemList);
			}

			// 正确答案
			questionInfoDto.setAnswer(tmxxEntity.getTmda());

			// 题目分值
			questionInfoDto.setScore(Integer.parseInt(tmxxEntity.getTmfz()));

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
	private List<String> getItemInfo(String itemInfo) {
		List<String> resultList = new ArrayList<String>();

		char ch = 'A';
		int charInt = ch;

		for (int i = charInt + 1; i < charInt + 26; i++) {
			char temp = (char) i;
			String strTemp = "|" + temp + "、";
			int itemaEndIndex = itemInfo.indexOf(strTemp);
			if (itemaEndIndex == -1) {
				resultList.add(itemInfo);
				return resultList;
			} else {
				resultList.add(itemInfo.substring(0, itemaEndIndex));
				itemInfo = itemInfo.substring(itemaEndIndex + 1);
			}
		}
		return resultList;
	}

}
