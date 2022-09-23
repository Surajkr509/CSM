package com.nixi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NixiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NixiApplication.class, args);
		System.err.println("::::Nixi Application::::");
	}

}
