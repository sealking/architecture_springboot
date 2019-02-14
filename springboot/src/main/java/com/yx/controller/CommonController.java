package com.yx.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yx.dto.common.DataTypeInfoByDetailCodeInDto;
import com.yx.dto.common.DataTypeInfoByDetailCodeOutDto;
import com.yx.dto.common.DataTypeInfoInDto;
import com.yx.dto.common.DataTypeInfoOutDto;
import com.yx.dto.common.FileDownLoadInDto;
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
	 * 文件下载
	 * 
	 * @param inDto
	 * @return
	 */
	@RequestMapping("/fileDownload")
	public void fileDownload(FileDownLoadInDto inDto, HttpServletResponse response) {
        String fullPath = "E:/testDownload/test1.docx";
        File downloadFile = new File(fullPath);

        response.setContentLength((int) downloadFile.length());

        // Copy the stream to the response's output stream.
        try {
            // set headers for the response
            String headerKey = "Content-Disposition";
            String headerValue = "attachment;filename="+ URLEncoder.encode(fullPath, "UTF-8");
            response.setHeader(headerKey, headerValue);
            InputStream myStream = new FileInputStream(fullPath);
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
