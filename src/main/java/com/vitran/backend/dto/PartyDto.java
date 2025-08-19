package com.vitran.backend.dto;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Single DTO for create and update operations.
 * Any field can be null → we decide in the service layer what to set or ignore.
 */
public record PartyDto(
        Long partyId,
        String partyType,          // PERSON / ORGANIZATION
        String partyStatus,        // ACTIVE / INACTIVE / SUSPENDED
        Set<String> roles,         // ADMIN / WORKER / BUSINESS / CUSTOMER / DEVELOPER
        String firstName,
        String lastName,
        String gender,
        String orgName,
        LocalDateTime fromDate,
        LocalDateTime thruDate
) {}
