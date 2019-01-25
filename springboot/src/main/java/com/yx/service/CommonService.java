package com.yx.service;

import com.yx.dto.common.DataTypeInfoByDetailCodeInDto;
import com.yx.dto.common.DataTypeInfoByDetailCodeOutDto;
import com.yx.dto.common.DataTypeInfoInDto;
import com.yx.dto.common.DataTypeInfoOutDto;

public interface CommonService {
	// 根据数据类型编码获取所有的数据类型明细信息
	public DataTypeInfoOutDto getDataTypeInfo(DataTypeInfoInDto inDto);
	
	// 根据数据类型编码及数据类型明细编码获取数据字典信息
	public DataTypeInfoByDetailCodeOutDto getDataTypeInfoByDetailCode(DataTypeInfoByDetailCodeInDto inDto);

}
