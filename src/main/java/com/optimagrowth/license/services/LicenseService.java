package com.optimagrowth.license.services;

import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.model.License;

@Service
public class LicenseService {

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ServiceConfig config;
	
	public License getLicense(String licenseId,String organizationId)
	{
		License license= new License();
		license.setId(new Random().nextInt(1000));
		license.setOrganizationId(organizationId);
		license.setLicenseId(licenseId);
		license.setDescription("Software Product!"+ "-" +config.getEnvtype());
		license.setProductName("Ostock");
		license.setLicenseType("full");
		
		return license;
	
	}
	
	
	
	public String createLicense(License license, String organizationId,Locale locale)
	{
		String responseMessage= null;
		if(license!=null)
		{
			license.setOrganizationId(organizationId);
//			responseMessage= String.format("This is the post and the object is: %s", license.toString());
			responseMessage= String.format(messageSource.getMessage("license.create.message", null,locale),license.toString());
			
		}
		return responseMessage;
	}
	
	public String updateLicense(License license, String organizationId,Locale locale)
	{
		String responseMessage= null;
		if(license!=null)
		{
			license.setOrganizationId(organizationId);
//			responseMessage= String.format("This is the put and the object is: %s", license.toString());
			responseMessage= String.format(messageSource.getMessage("license.update.message",null,null), license.toString());
		}
		return responseMessage;
	}
	
	public String deleteLicense(String licenseId, String organizationId,Locale locale)
	{
		String responseMessage= null;
		
			
//		responseMessage = String.format("Deleteing license with id %s  for organizationId %s", licenseId,
//				organizationId);
		responseMessage = String.format(messageSource.getMessage("license.delete.message", null,locale), licenseId,
				organizationId);
			
	
		return responseMessage;
	}
	
	
}
