package com.optimagrowth.license.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom")
public class ServiceConfig {

	public ServiceConfig()
	{
		
	}
	
	private String envtype;

	public String getEnvtype() {
		return envtype;
	}

	public void setEnvtype(String envtype) {
		this.envtype = envtype;
	}
	
	
	
}
