package com.yx.dto.common;

import java.util.List;

import com.yx.dto.BaseOutDto;

import lombok.Data;

@Data
public class DataTypeInfoOutDto extends BaseOutDto {
	
	// 数据字典项目
	private List<DataTypeInfoDto> dataTypeList;
	
}
