package com.axoulotl.alextheque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
public class AlexthequeApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AlexthequeApplication.class, args);
	}

}
