package com.yx.dto.examination;

import com.yx.dto.BaseOutDto;

import lombok.Data;

@Data
public class GetTrainByStuNoOutDto extends BaseOutDto {

	// id
	private String id;

	// 培训编号
	private String no;

	// 培训名称
	private String name;
}
