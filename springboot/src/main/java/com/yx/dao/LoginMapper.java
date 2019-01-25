package com.yx.dao;

import com.yx.entity.GetXyxxByIdCardEntity;
import com.yx.entity.GetXyxxByIdCardParm;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
	
	// 根据身份证检索学员信息
	public List<GetXyxxByIdCardEntity> getXyxxByIdcard(GetXyxxByIdCardParm parm);
	
}