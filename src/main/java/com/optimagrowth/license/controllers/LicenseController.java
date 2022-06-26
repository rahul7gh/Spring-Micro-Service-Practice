package com.optimagrowth.license.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.services.LicenseService;

@RestController
@RequestMapping(value="v1/organization/{organizationId}/license")
public class LicenseController {
	
	@Autowired
	LicenseService licenseService;
	
	@Autowired RestTemplate loadBalamcedRestTemplate;
	
	@Autowired
	DiscoveryClient discoveryClient;
	
	@Autowired
	OrganizatonServiceFeignClient feignClient;
	
	@RequestMapping(value="/{licenseId}",method = RequestMethod.GET)
	public ResponseEntity<License> getLicense(@PathVariable String organizationId,@PathVariable String licenseId)
	{
		License license=licenseService.getLicense(licenseId, organizationId);
		
		license.add(
				linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId())).withSelfRel(),
				linkTo(methodOn(LicenseController.class).createLicense(license, organizationId, null)).withRel("createLicense")
				);
		
		return ResponseEntity.ok(license);
	}
	
	@PostMapping
	public ResponseEntity<String> createLicense(@RequestBody License license,@PathVariable String organizationId,
			@RequestHeader(value="Accept-Language",required = false) Locale locale
			)
	{
		return ResponseEntity.ok(licenseService.createLicense(license, organizationId,locale));
	}
	
	@DeleteMapping("/{licenseId}")
	public ResponseEntity<String> deleteLicense(@PathVariable String organizationId,@PathVariable String licenseId,
			@RequestHeader(value="Accept-Language",required = false) Locale locale)
	{
		return ResponseEntity.ok(licenseService.deleteLicense(licenseId, organizationId,locale));
	}
	
	@PutMapping
	public ResponseEntity<String> updateLicense(@PathVariable String organizationId,@RequestBody License license,
			@RequestHeader(value="Accept-Language",required = false) Locale locale)
	{
		return ResponseEntity.ok(licenseService.updateLicense(license, organizationId,locale));
	}
	
	@GetMapping("/sd/{eurekaClient}")
	public ResponseEntity<String> inokeOrgUsingEUrekaClient(@PathVariable String organizationId,@PathVariable String eurekaClient)
	{
		String result=null;
		
		if(eurekaClient.equals("spring"))
		{
			RestTemplate restTemplate= new RestTemplate();
			List<ServiceInstance> instances = discoveryClient.getInstances("organization-service");
			if (instances.size() == 0)
				return null;
			String serviceUri = String.format("%s/organization-service/v1/getid", instances.get(0).getUri().toString(),
					organizationId);
//			System.out.println(serviceUri);
			
			ResponseEntity<String> restExchange =
					 restTemplate.exchange(
					 serviceUri, HttpMethod.GET,
					 null, String.class, organizationId);
			
			result=restExchange.getBody();
			
			/*
			 * we are calling the dicovery client by orselves and also picking the instance
			 * by ourselves there by not making use of client-sdie load balancing.
			 */
		}
		else if(eurekaClient.equals("spring-lb"))
		{
			
//			we use here load balanced rest template; so we don't provie the service uri by choosing one of the instances instead
//			we jsut provide the rest template class the ID of the service we  want to connect.
			
			ResponseEntity<String> restExchange =
					 loadBalamcedRestTemplate.exchange(
					 "http://organization-service/organization-service/v1/getid",
					 HttpMethod.GET, null,
					 String.class, organizationId);
			result=restExchange.getBody();
			
			/*
			 * we are calling the dicovery client by orselves and also picking the instance
			 * by ourselves there by not making use of client-sdie load balancing.
			 */
		}
		else if(eurekaClient.equals("feign"))
		{
			result=feignClient.getOrganizationId();
		}
		
		
		return ResponseEntity.ok(result);
	}
	
}
