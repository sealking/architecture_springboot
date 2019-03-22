package com.yx.entity;

import lombok.Data;

@Data
public class GetXyxxByIdCardEntity {

	// ID
	private String id;
	
	// 学员姓名
	private String xyxm;
	
	// 学员性别
	private String xyxb;
	
	// 证件号码
	private String zjhm;
	
	// 民族
	private String mz;
	
	// 学员年龄
	private int xynl;
	
	// 联系电话
	private String lxdh;
	
	// 培训编号
	private String pxbh;
	
	// 是否特种作业人员
	private String sftzzy;
	
	// 家庭地址
	private String jtdz;
	
	// 学员照片
	private String xyzp;
	
	// 学员类别
	private String xylb;
	
	// 所属单位
	private String ssdw;
	
	// 所属单位Id
	private String ssdwId;
	
	// 身体状况
	private String stzk;
}
