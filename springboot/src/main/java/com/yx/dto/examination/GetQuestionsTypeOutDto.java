package com.yx.dto.examination;

import com.yx.dto.BaseOutDto;

import lombok.Data;

@Data
public class GetQuestionsTypeOutDto extends BaseOutDto {

	// 题库种类信息
	private String questionsType;
}
