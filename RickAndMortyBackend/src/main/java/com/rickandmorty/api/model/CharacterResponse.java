package com.rickandmorty.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterResponse {
    private Info info;
    private List<Character> results;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {
        private Integer count;
        private Integer pages;
        private String next;
        private String prev;
    }
}