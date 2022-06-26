package com.optimagrowth.license.controllers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("organization-service")
public interface OrganizatonServiceFeignClient {

	@GetMapping("/organization-service/v1/getid")
	String getOrganizationId();
	
	
}
