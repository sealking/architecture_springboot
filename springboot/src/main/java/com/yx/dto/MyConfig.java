package com.yx.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(prefix="myconfig")
@Data
public class MyConfig {

	private String serverUrl;
}
