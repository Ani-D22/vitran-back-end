package com.vitran.backend.dto;

public record AuthRequest(
        String userLoginId,
        String password
        ) {
}
