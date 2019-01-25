package com.yx.dto.common;

import lombok.Data;

@Data
public class DataTypeInfoByDetailCodeOutDto {

	// 数据类型编码
	private String typeCode;
	
	// 数据类型明细编码
	private String typeDetailCode;
	
	// 数据类型明细名称
	private String typeDetailName;
}
