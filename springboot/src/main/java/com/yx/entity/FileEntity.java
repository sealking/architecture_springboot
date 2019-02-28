package com.yx.entity;

import java.util.Date;
import lombok.Data;

@Data
public class FileEntity {
	// 文件ID
	private String id;
	
	// 文件路径
	private String filePath;
	
	// 文件名
	private String fileName;
	
	// 上传时间
	private Date uploadTime;
}
