package com.vitran.backend.dto;

public record LoginResponse(
        AuthProfile profile,
        String accessToken
        ) {
}