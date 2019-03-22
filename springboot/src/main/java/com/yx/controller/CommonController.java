package com.yx.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yx.dto.common.DataTypeInfoByDetailCodeInDto;
import com.yx.dto.common.DataTypeInfoByDetailCodeOutDto;
import com.yx.dto.common.DataTypeInfoInDto;
import com.yx.dto.common.DataTypeInfoOutDto;
import com.yx.dto.common.FileInfoOutDto;
import com.yx.service.CommonService;

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
	
	/**
	 * 获取文件信息
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/getFileInfo")
	public List<FileInfoOutDto> getFileInfo(String fileTypeKey) {
		List<FileInfoOutDto> outDtoList = commonService.getFileInfo(fileTypeKey);
        return outDtoList;
	}
}
