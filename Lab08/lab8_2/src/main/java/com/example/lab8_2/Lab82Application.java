package com.example.lab8_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab82Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Lab82Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		@Autowired
	}
}
