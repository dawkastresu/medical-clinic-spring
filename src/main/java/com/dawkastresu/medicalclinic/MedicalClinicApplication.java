package com.dawkastresu.medicalclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.dawkastresu.medicalclinic.service",
		"com.dawkastresu.medicalclinic.repository",
		"com.dawkastresu.medicalclinic.utils"
})
public class MedicalClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalClinicApplication.class, args);
	}

}
