package com.optimagrowth.license.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.services.LicenseService;

@RestController
@RequestMapping(value="v1/organization/{organizationId}/license")
public class LicenseController {
	
	@Autowired
	LicenseService licenseService;
	
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
	
}
