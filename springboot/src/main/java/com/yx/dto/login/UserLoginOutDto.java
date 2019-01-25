package com.yx.dto.login;

import java.util.List;

import com.yx.dto.BaseOutDto;
import com.yx.dto.examination.QuestionInfoDto;

import lombok.Data;

@Data
public class UserLoginOutDto extends BaseOutDto {

	// 学员编号
	private String studentNo;
	
	// 身份证号
	private String idCard;
	
	// 姓名
	private String name;
	
	// 所属单位
	private String units;
	
}
