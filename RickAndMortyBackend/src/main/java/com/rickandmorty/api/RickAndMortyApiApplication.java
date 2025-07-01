package com.rickandmorty.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class RickAndMortyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickAndMortyApiApplication.class, args);
	}
	
	@Bean
	public WebClient webClient() {
		return WebClient.builder().baseUrl("https://rickandmortyapi.com/api").build();
	}
}