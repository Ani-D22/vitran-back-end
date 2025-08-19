package com.vitran.backend.dto;

public record AuthProfile(
        String userLoginId,
        Long partyId,
        String firstName
        ) {
}