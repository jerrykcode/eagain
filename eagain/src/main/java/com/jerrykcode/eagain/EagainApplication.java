package com.jerrykcode.eagain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EagainApplication {

	public static void main(String[] args) {
		SpringApplication.run(EagainApplication.class, args);
	}

}
