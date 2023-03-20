package com.skypro.auction;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class EasyAuctionApp {

	public static void main(String[] args) {
		SpringApplication.run(EasyAuctionApp.class, args);
	}

}
