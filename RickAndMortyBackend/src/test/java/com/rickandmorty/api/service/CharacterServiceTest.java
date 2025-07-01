package com.rickandmorty.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.rickandmorty.api.model.Character;
import com.rickandmorty.api.model.CharacterResponse;
import com.rickandmorty.api.model.Location;
import com.rickandmorty.api.model.Origin;
import com.rickandmorty.api.exception.ResourceNotFoundException;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class CharacterServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    private CharacterService characterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        characterService = new CharacterService(webClient);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersUriSpec.uri(any(java.util.function.Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void getAllCharacters_Success() {
        // Arrange
        CharacterResponse expectedResponse = new CharacterResponse();
        when(responseSpec.bodyToMono(CharacterResponse.class)).thenReturn(Mono.just(expectedResponse));

        // Act & Assert
        StepVerifier.create(characterService.getAllCharacters(1))
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    void getCharacterById_Success() {
        // Arrange
        Character expectedCharacter = createMockCharacter();
        when(responseSpec.bodyToMono(Character.class)).thenReturn(Mono.just(expectedCharacter));

        // Act & Assert
        StepVerifier.create(characterService.getCharacterById(1))
                .expectNext(expectedCharacter)
                .verifyComplete();
    }

    @Test
    void getCharacterById_NotFound() {
        // Arrange
        when(responseSpec.bodyToMono(Character.class)).thenReturn(Mono.error(new ResourceNotFoundException("Character not found")));

        // Act & Assert
        StepVerifier.create(characterService.getCharacterById(999))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    void searchCharactersByName_Success() {
        // Arrange
        CharacterResponse expectedResponse = new CharacterResponse();
        when(responseSpec.bodyToMono(CharacterResponse.class)).thenReturn(Mono.just(expectedResponse));

        // Act & Assert
        StepVerifier.create(characterService.searchCharactersByName("Rick"))
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    void searchCharactersByName_NotFound() {
        // Arrange
        when(responseSpec.bodyToMono(CharacterResponse.class))
                .thenReturn(Mono.error(new ResourceNotFoundException("No characters found")));

        // Act & Assert
        StepVerifier.create(characterService.searchCharactersByName("NonExistentCharacter"))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    private Character createMockCharacter() {
        Character character = new Character();
        character.setId(1);
        character.setName("Rick Sanchez");
        character.setStatus("Alive");
        character.setSpecies("Human");
        character.setType("");
        character.setGender("Male");
        
        Origin origin = new Origin();
        origin.setName("Earth");
        origin.setUrl("https://rickandmortyapi.com/api/location/1");
        character.setOrigin(origin);

        Location location = new Location();
        location.setName("Earth");
        location.setUrl("https://rickandmortyapi.com/api/location/20");
        character.setLocation(location);

        character.setImage("https://rickandmortyapi.com/api/character/avatar/1.jpeg");
        character.setEpisode(Arrays.asList("https://rickandmortyapi.com/api/episode/1"));
        character.setUrl("https://rickandmortyapi.com/api/character/1");
        character.setCreated("2017-11-04T18:48:46.250Z");

        return character;
    }
}