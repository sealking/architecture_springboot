package com.yx.dto.common;

import com.yx.dto.BaseOutDto;

import lombok.Data;

@Data
public class FileInfoOutDto extends BaseOutDto {
	// 文件ID
	private String id;
	
	// 文件路径
	private String filePath;
	
	// 文件名
	private String fileName;
	
	// 上传时间
	private String uploadTime;
}
