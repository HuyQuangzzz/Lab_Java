package com.example.Lab8_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Lab81Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab81Application.class, args);
	}

}
