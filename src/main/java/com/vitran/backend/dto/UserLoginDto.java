package com.vitran.backend.dto;

import java.util.Set;

public record UserLoginDto(
        String userLoginId,
        String password,
        String passwordHint,
        Long partyId,
        Set<String> permissions // e.g. ["VIEW_ORDERS", "CREATE_AND_UPDATE_ORDERS"]
) {}
