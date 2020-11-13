package com.lti.generalinsurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.lti"})
@EntityScan(basePackages = "com.lti")

public class GeneralInsuranceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneralInsuranceAppApplication.class, args);
		System.out.println("General Insurance is working");
	}

}
