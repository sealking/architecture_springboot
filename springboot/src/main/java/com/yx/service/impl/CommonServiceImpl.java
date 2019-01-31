package com.yx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yx.dao.CommonMapper;
import com.yx.dao.LoginMapper;
import com.yx.dto.common.DataTypeInfoByDetailCodeInDto;
import com.yx.dto.common.DataTypeInfoByDetailCodeOutDto;
import com.yx.dto.common.DataTypeInfoDto;
import com.yx.dto.common.DataTypeInfoInDto;
import com.yx.dto.common.DataTypeInfoOutDto;
import com.yx.entity.GetDataTypeInfoByDetailCodeParm;
import com.yx.entity.GetDataTypeDetailEntity;
import com.yx.entity.GetDataTypeDetailParm;
import com.yx.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonMapper commonMapper;
	
	/**
	 * 根据数据类型编码获取所有的数据类型明细信息
	 * 
	 * @param inDto
	 * @return
	 */
	@Override
	public DataTypeInfoOutDto getDataTypeInfo(DataTypeInfoInDto inDto) {
		DataTypeInfoOutDto outDto = new DataTypeInfoOutDto();
		List<DataTypeInfoDto> dataTypeList = new ArrayList<DataTypeInfoDto>();
		
		GetDataTypeDetailParm getDataTypeDetailParm = new GetDataTypeDetailParm();
		getDataTypeDetailParm.setTypeCode(inDto.getTypeCode());
		List<GetDataTypeDetailEntity> getDataTypeDetailList = commonMapper.getDataTypeInfo(getDataTypeDetailParm);
		for(GetDataTypeDetailEntity getDataTypeDetailEntity : getDataTypeDetailList) {
			DataTypeInfoDto dataTypeInfoDto = new DataTypeInfoDto();
			dataTypeInfoDto.setKey(getDataTypeDetailEntity.getTypeDetailCode());
			dataTypeInfoDto.setValue(getDataTypeDetailEntity.getTypeDetailName());
			dataTypeList.add(dataTypeInfoDto);
		}
		outDto.setDataTypeList(dataTypeList);
		return outDto;
	}

	/**
	 * 根据数据类型编码及数据类型明细编码获取数据字典信息
	 * 
	 * @param inDto
	 * @return
	 */
	@Override
	public DataTypeInfoByDetailCodeOutDto getDataTypeInfoByDetailCode(
			DataTypeInfoByDetailCodeInDto inDto) {
		DataTypeInfoByDetailCodeOutDto outDto = new DataTypeInfoByDetailCodeOutDto();
		GetDataTypeInfoByDetailCodeParm parm = new GetDataTypeInfoByDetailCodeParm();
		parm.setTypeCode(inDto.getTypeCode());
		parm.setTypeDetailCode(inDto.getTypeDetailCode());
		String typeDetailName = commonMapper.getDataTypeInfoByDetailCode(parm);
		
		if(!StringUtils.isEmpty(typeDetailName)) {
			outDto.setTypeCode(inDto.getTypeCode());
			outDto.setTypeDetailCode(inDto.getTypeDetailCode());
			outDto.setTypeDetailName(typeDetailName);
		}
		
		return outDto;
	}
}
