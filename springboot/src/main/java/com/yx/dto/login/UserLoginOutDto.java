package com.yx.dto.login;

import com.yx.dto.BaseOutDto;
import lombok.Data;

@Data
public class UserLoginOutDto extends BaseOutDto {

	// 学员编号
	private String studentNo;
	
	// 身份证号
	private String idCard;
	
	// 姓名
	private String name;
	
	// 性别
	private String sex;
	
	// 所属单位
	private String units;
	
	// 所属单位
	private String unitsId;
	
	// 学员类别
	private String studentType;
	
}
