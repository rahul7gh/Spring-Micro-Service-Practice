package com.optimagrowth.license.beans;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class InternationlizationBeans {

	@Bean
	public LocaleResolver getLocaleResolver()
	{
		SessionLocaleResolver localeResolver= new SessionLocaleResolver();
		
//		Set US as Default Locale
		localeResolver.setDefaultLocale(Locale.US);
		
		return localeResolver;
		
	}
	
	@Bean
	public ResourceBundleMessageSource getBundleMessageSource()
	{
		ResourceBundleMessageSource msgSrc= new ResourceBundleMessageSource();
		
//		doesn't throw error if  a message is not found instead it returns  message code.
		msgSrc.setUseCodeAsDefaultMessage(true);
		
//		BaseName of language properties File
		msgSrc.setBasenames("messages");
		return msgSrc;
	}
	
	
}
