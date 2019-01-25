package com.yx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yx.dto.common.DataTypeInfoByDetailCodeInDto;
import com.yx.dto.common.DataTypeInfoByDetailCodeOutDto;
import com.yx.dto.common.DataTypeInfoInDto;
import com.yx.dto.common.DataTypeInfoOutDto;
import com.yx.dto.login.UserLoginInDto;
import com.yx.dto.login.UserLoginOutDto;
import com.yx.service.CommonService;
import com.yx.service.LoginService;

@RestController
@RequestMapping("/common")
public class CommonController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 根据数据类型编码获取所有的数据类型明细信息
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getDataTypeInfo")
    public DataTypeInfoOutDto getDataTypeInfo(DataTypeInfoInDto inDto) {
		DataTypeInfoOutDto outDto = commonService.getDataTypeInfo(inDto);
		return outDto;
	}
	
	
	/**
	 * 根据数据类型编码及数据类型明细编码获取数据字典信息
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getDataTypeInfoByDetailCode")
	public DataTypeInfoByDetailCodeOutDto getDataTypeInfoByDetailCode(DataTypeInfoByDetailCodeInDto inDto) {
		DataTypeInfoByDetailCodeOutDto outDto = commonService.getDataTypeInfoByDetailCode(inDto);
		return outDto;
	}
	
}
