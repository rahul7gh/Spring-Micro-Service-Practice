package com.optimagrowth.license.controllers;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.services.LicenseService;

import io.micrometer.core.ipc.http.HttpSender.Method;

@RestController
@RequestMapping(value="v1/organization/{organizationId}/license")
public class LicenseController {
	
	@Autowired
	LicenseService licenseService;
	
	@RequestMapping(value="/{licenseId}",method = RequestMethod.GET)
	public ResponseEntity<License> getLicense(@PathVariable String organizationId,@PathVariable String licenseId)
	{
		License license=licenseService.getLicense(licenseId, organizationId);
		
		return ResponseEntity.ok(license);
	}
	
	@PostMapping
	public ResponseEntity<String> createLicense(@RequestBody License license,@PathVariable String organizationId)
	{
		return ResponseEntity.ok(licenseService.createLicense(license, organizationId));
	}
	
	@DeleteMapping("/{licenseId}")
	public ResponseEntity<String> deleteLicense(@PathVariable String organizationId,@PathVariable String licenseId )
	{
		return ResponseEntity.ok(licenseService.deleteLicense(licenseId, organizationId));
	}
	
	@PutMapping
	public ResponseEntity<String> updateLicense(@PathVariable String organizationId,@RequestBody License license )
	{
		return ResponseEntity.ok(licenseService.updateLicense(license, organizationId));
	}
	
}
