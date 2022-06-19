package com.optimagrowth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SpringMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMicroservicesApplication.class, args);
	}

}
