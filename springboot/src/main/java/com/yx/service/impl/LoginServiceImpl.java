package com.yx.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yx.dao.LoginMapper;
import com.yx.dto.login.UserLoginInDto;
import com.yx.dto.login.UserLoginOutDto;
import com.yx.entity.GetXyxxByIdCardEntity;
import com.yx.entity.GetXyxxByIdCardParm;
import com.yx.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;
	
	/**
	 * 登录
	 * 
	 * @param inDto
	 * @return
	 */
	@Override
	public UserLoginOutDto userLogin(UserLoginInDto userLoginInDto) {
		
		UserLoginOutDto userLoginOutDto = new UserLoginOutDto();
		
		// 根据身份证号判断用户是否存在
		GetXyxxByIdCardParm getXyxxByIdCardParm = new GetXyxxByIdCardParm();
		getXyxxByIdCardParm.setZjhm(userLoginInDto.getUserIdcard());
		List<GetXyxxByIdCardEntity> getXyxxByIdCardEntityList = loginMapper.getXyxxByIdcard(getXyxxByIdCardParm);
		if(getXyxxByIdCardEntityList.size() > 0) {
			// 获取学员信息
			GetXyxxByIdCardEntity XyxxEntity = getXyxxByIdCardEntityList.get(0);
			// 学员编号
			userLoginOutDto.setStudentNo(XyxxEntity.getId());
			// 身份证号码
			userLoginOutDto.setIdCard(XyxxEntity.getZjhm());
			// 姓名
			userLoginOutDto.setName(XyxxEntity.getXyxm());
			// 性别
			userLoginOutDto.setSex(XyxxEntity.getXyxb());
			// 所属单位
			userLoginOutDto.setUnits(XyxxEntity.getSsdw());
			// 所属单位Id
			userLoginOutDto.setUnitsId(XyxxEntity.getSsdwId());
			// 学员类别
			userLoginOutDto.setStudentType(XyxxEntity.getXylb());
			
			userLoginOutDto.setReturnCode("0");
			userLoginOutDto.setMsg("登录成功");
		} else {
			userLoginOutDto.setReturnCode("1");
			userLoginOutDto.setMsg("该身份证号不存在，请与管理员联系！");
			return userLoginOutDto;
		}
		return userLoginOutDto;
	}
	
//	@Override
//	public UserLoginOutDto userLogin(UserLoginInDto userLoginInDto) {
//		
//		UserLoginOutDto userLoginOutDto = new UserLoginOutDto();
//		
//		// 根据身份证号判断用户是否存在
//		GetXyxxByIdCardParm getXyxxByIdCardParm = new GetXyxxByIdCardParm();
//		getXyxxByIdCardParm.setZjhm(userLoginInDto.getUserIdcard());
//		List<GetXyxxByIdCardEntity> getXyxxByIdCardEntityList = loginMapper.getXyxxByIdcard(getXyxxByIdCardParm);
//		if(getXyxxByIdCardEntityList.size() > 0) {
//			// 获取学员信息
//			GetXyxxByIdCardEntity XyxxEntity = getXyxxByIdCardEntityList.get(0);
//			
//			// 设置学员编号
//			GetTmxxParm getTmxxParm = new GetTmxxParm();
//			getTmxxParm.setStudentNo(XyxxEntity.getId());
//			
//			List<GetTmxxEntity> tmxxEntityList = new ArrayList<GetTmxxEntity>();
//			// 1：在线考试，2：模拟考试
//			if("1".equals(userLoginInDto.getExaminationType())) {
//				// 试题信息
//				tmxxEntityList = loginMapper.getTmxxForZxksByStudentNo(getTmxxParm);
//				// 考试时长
//				HashMap<String, String> ExaminationTime = loginMapper.getExaminationTimeByStudentNo(getTmxxParm);
//				String beginTime = ExaminationTime.get("kssj");
//				String endTime = ExaminationTime.get("jssj");
//				
//				if(StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
//					userLoginOutDto.setReturnCode("1");
//					userLoginOutDto.setMsg("考试时间未设置，请与管理员联系！");
//					return userLoginOutDto;
//				} else {
//					SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//					beginTime = "2019-01-01 " + beginTime;
//					endTime = "2019-01-01 " + endTime;
//					
//					try {
//						Date fromDate1 = simpleFormat.parse(beginTime);
//						Date toDate1 = simpleFormat.parse(endTime);
//						long from = fromDate1.getTime();  
//					    long to = toDate1.getTime();  
//					    int examinationMinute = (int) ((to - from) / (1000 * 60));  
//					    userLoginOutDto.setExaminationMinute(examinationMinute);
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}  
//				}
//				
//			} else {
//				// 试题信息
//				tmxxEntityList = loginMapper.getTmxxForMnksByStudentNo(getTmxxParm);
//				// 考试时长
//				userLoginOutDto.setExaminationMinute(90);
//			}
//			
//			// 获取试题失败
//			if(tmxxEntityList.size() == 0) {
//				userLoginOutDto.setReturnCode("1");
//				userLoginOutDto.setMsg("试题不存在，请与管理员联系！");
//				return userLoginOutDto;
//			} else {
//				// 身份证号码
//				userLoginOutDto.setIdCard(XyxxEntity.getZjhm());
//				// 姓名
//				userLoginOutDto.setName(XyxxEntity.getXyxm());
//				// 所属单位
//				userLoginOutDto.setUnits(XyxxEntity.getSsdw());
//				
//				// 封装试题信息
//				List<QuestionInfoDto> questionInfoList = new ArrayList<QuestionInfoDto>();
//				for(GetTmxxEntity tmxxEntity : tmxxEntityList) {
//					QuestionInfoDto questionInfoDto = new QuestionInfoDto();
//					
//					// 题目编号
//					questionInfoDto.setQuestionNo(tmxxEntity.getTmbh());
//					
//					// 题目种类
//					questionInfoDto.setQuestionSort(tmxxEntity.getTmzl());
//					
//					// 题目类型
//					questionInfoDto.setQuestionType(tmxxEntity.getTmlx());
//					
//					// 题目内容
//					questionInfoDto.setQuestionContent(tmxxEntity.getTmnr());
//					
//					if("1".equals(tmxxEntity.getTmlx()) || "2".equals(tmxxEntity.getTmlx())) {
//						// 选项内容List
//						String itemInfo = tmxxEntity.getTmxx();
//						List<String> itemInfoList = this.getItemInfo(itemInfo);
//						questionInfoDto.setItemInfoList(itemInfoList);
//						
//						// 选项List
//						char ch = 'A';
//						int charInt = ch;
//						List<String> itemList = new ArrayList<String>();
//						for(int i = 0; i < itemInfoList.size(); i++) {
//							itemList.add(String.valueOf((char)charInt++));
//						}
//						questionInfoDto.setItemList(itemList);
//					}
//					
//					// 正确答案
//					questionInfoDto.setAnswer(tmxxEntity.getTmda());
//					
//					// 题目分值
//					questionInfoDto.setScore(Integer.parseInt(tmxxEntity.getTmfz()));
//					
//					questionInfoList.add(questionInfoDto);
//				}
//				userLoginOutDto.setQuestionInfoList(questionInfoList);
//				userLoginOutDto.setReturnCode("0");
//				userLoginOutDto.setMsg("登录成功");
//			}
//		} else {
//			userLoginOutDto.setReturnCode("1");
//			userLoginOutDto.setMsg("该身份证号不存在，请与管理员联系！");
//			return userLoginOutDto;
//		}
//		return userLoginOutDto;
//	}


}
