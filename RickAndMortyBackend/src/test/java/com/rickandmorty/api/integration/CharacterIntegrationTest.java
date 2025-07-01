package com.rickandmorty.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.rickandmorty.api.model.Character;
import com.rickandmorty.api.model.CharacterResponse;
import com.rickandmorty.api.service.CharacterService;

import reactor.test.StepVerifier;

@SpringBootTest
@ActiveProfiles("test")
class CharacterIntegrationTest {

    @Autowired
    private CharacterService characterService;

    @Test
    void testGetAllCharacters() {
        StepVerifier.create(characterService.getAllCharacters(1))
                .expectNextMatches(response -> {
                    // Verificar que la respuesta contiene datos válidos
                    return response != null &&
                           response.getResults() != null &&
                           !response.getResults().isEmpty() &&
                           response.getResults().get(0).getId() != null &&
                           response.getResults().get(0).getName() != null;
                })
                .verifyComplete();
    }

    @Test
    void testGetCharacterById() {
        StepVerifier.create(characterService.getCharacterById(1))
                .expectNextMatches(character -> {
                    // Verificar que los datos del personaje son válidos
                    return character != null &&
                           character.getId() == 1 &&
                           "Rick Sanchez".equals(character.getName()) &&
                           character.getStatus() != null &&
                           character.getSpecies() != null;
                })
                .verifyComplete();
    }

    @Test
    void testSearchCharactersByName() {
        StepVerifier.create(characterService.searchCharactersByName("Rick"))
                .expectNextMatches(response -> {
                    // Verificar que la búsqueda retorna resultados válidos
                    return response != null &&
                           response.getResults() != null &&
                           !response.getResults().isEmpty() &&
                           response.getResults().stream()
                                   .anyMatch(character -> 
                                       character.getName().toLowerCase()
                                               .contains("rick"));
                })
                .verifyComplete();
    }
}