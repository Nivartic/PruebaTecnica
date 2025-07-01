package com.rickandmorty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rickandmorty.api.model.Character;
import com.rickandmorty.api.model.CharacterResponse;
import com.rickandmorty.api.exception.ResourceNotFoundException;

import reactor.core.publisher.Mono;

@Service
public class CharacterService {

    private final WebClient webClient;

    @Autowired
    public CharacterService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<CharacterResponse> getAllCharacters(Integer page) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/character")
                    .queryParamIfPresent("page", java.util.Optional.ofNullable(page))
                    .build())
                .retrieve()
                .bodyToMono(CharacterResponse.class);
    }

    public Mono<Character> getCharacterById(Integer id) {
        return webClient.get()
                .uri("/character/" + id)
                .retrieve()
                .bodyToMono(Character.class)
                .onErrorResume(error -> Mono.error(new ResourceNotFoundException("Personaje no encontrado con ID: " + id)));
    }

    public Mono<CharacterResponse> searchCharactersByName(String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/character")
                    .queryParam("name", name)
                    .build())
                .retrieve()
                .bodyToMono(CharacterResponse.class)
                .onErrorResume(error -> Mono.error(new ResourceNotFoundException("No se encontraron personajes con el nombre: " + name)));
    }
}