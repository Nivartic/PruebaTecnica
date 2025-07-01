package com.rickandmorty.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.rickandmorty.api.model.Character;
import com.rickandmorty.api.model.CharacterResponse;
import com.rickandmorty.api.model.Location;
import com.rickandmorty.api.model.Origin;
import com.rickandmorty.api.service.CharacterService;
import com.rickandmorty.api.exception.ResourceNotFoundException;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CharacterControllerTest {

    @Mock
    private CharacterService characterService;

    @InjectMocks
    private CharacterController characterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCharacters_Success() {
        // Arrange
        CharacterResponse expectedResponse = new CharacterResponse();
        when(characterService.getAllCharacters(any())).thenReturn(Mono.just(expectedResponse));

        // Act & Assert
        StepVerifier.create(characterController.getAllCharacters(1))
                .expectNextMatches(response -> 
                    response.getStatusCode().is2xxSuccessful() &&
                    response.getBody().equals(expectedResponse))
                .verifyComplete();
    }

    @Test
    void getCharacterById_Success() {
        // Arrange
        Character expectedCharacter = createMockCharacter();
        when(characterService.getCharacterById(1)).thenReturn(Mono.just(expectedCharacter));

        // Act & Assert
        StepVerifier.create(characterController.getCharacterById(1))
                .expectNextMatches(response -> 
                    response.getStatusCode().is2xxSuccessful() &&
                    response.getBody().equals(expectedCharacter))
                .verifyComplete();
    }

    @Test
    void getCharacterById_NotFound() {
        // Arrange
        when(characterService.getCharacterById(999))
                .thenReturn(Mono.error(new ResourceNotFoundException("Character not found")));

        // Act & Assert
        StepVerifier.create(characterController.getCharacterById(999))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    void searchCharactersByName_Success() {
        // Arrange
        CharacterResponse expectedResponse = new CharacterResponse();
        when(characterService.searchCharactersByName(anyString()))
                .thenReturn(Mono.just(expectedResponse));

        // Act & Assert
        StepVerifier.create(characterController.searchCharactersByName("Rick"))
                .expectNextMatches(response -> 
                    response.getStatusCode().is2xxSuccessful() &&
                    response.getBody().equals(expectedResponse))
                .verifyComplete();
    }

    @Test
    void searchCharactersByName_NotFound() {
        // Arrange
        when(characterService.searchCharactersByName("NonExistentCharacter"))
                .thenReturn(Mono.error(new ResourceNotFoundException("No characters found")));

        // Act & Assert
        StepVerifier.create(characterController.searchCharactersByName("NonExistentCharacter"))
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
        character.setEpisode(new String[]{"https://rickandmortyapi.com/api/episode/1"});
        character.setUrl("https://rickandmortyapi.com/api/character/1");
        character.setCreated("2017-11-04T18:48:46.250Z");

        return character;
    }
}