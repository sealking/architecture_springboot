package com.yx.entity;

import lombok.Data;

@Data
public class GetQuestionsSettingParm {
	// 工种code
	private String workTypeCode;
	
	// 题库编号
	private String questionBankId;
}
