package com.optimagrowth.orgnizationservice.controllers;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationServiceController {

	@GetMapping("/organization-service/v1/getid")
	public String getOrganizatinId()
	{
		Random r = new Random();
		return "Id"+System.nanoTime()+r.nextInt(1000090);
	}
	
}
