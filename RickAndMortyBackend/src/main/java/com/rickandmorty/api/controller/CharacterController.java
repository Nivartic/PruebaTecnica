package com.rickandmorty.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rickandmorty.api.model.Character;
import com.rickandmorty.api.model.CharacterResponse;
import com.rickandmorty.api.service.CharacterService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/characters")
@CrossOrigin(origins = "http://localhost:4200")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public Mono<ResponseEntity<CharacterResponse>> getAllCharacters(
            @RequestParam(required = false) Integer page) {
        return characterService.getAllCharacters(page)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Character>> getCharacterById(@PathVariable Integer id) {
        return characterService.getCharacterById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/search")
    public Mono<ResponseEntity<CharacterResponse>> searchCharactersByName(
            @RequestParam String name) {
        return characterService.searchCharactersByName(name)
                .map(ResponseEntity::ok);
    }
}