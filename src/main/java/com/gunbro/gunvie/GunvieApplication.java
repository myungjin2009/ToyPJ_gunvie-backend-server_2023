package com.gunbro.gunvie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication(exclude= SecurityAutoConfiguration.class)
public class GunvieApplication {

	public static void main(String[] args) {
		SpringApplication.run(GunvieApplication.class, args);
	}

}
