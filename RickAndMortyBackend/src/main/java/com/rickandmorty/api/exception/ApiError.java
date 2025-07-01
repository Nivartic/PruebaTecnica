package com.rickandmorty.api.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String message;
    private String details;
    private Integer status;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}