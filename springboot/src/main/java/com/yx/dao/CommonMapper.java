package com.yx.dao;

import com.yx.entity.GetDataTypeInfoByDetailCodeParm;
import com.yx.entity.GetDataTypeDetailEntity;
import com.yx.entity.GetDataTypeDetailParm;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {	
	// 获取数据字典信息
	public List<GetDataTypeDetailEntity> getDataTypeInfo(GetDataTypeDetailParm parm);
	
	// 根据数据类型编码及数据类型明细编码获取数据字典名称
	public String getDataTypeInfoByDetailCode(GetDataTypeInfoByDetailCodeParm parm);
}