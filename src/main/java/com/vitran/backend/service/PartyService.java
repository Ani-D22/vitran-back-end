package com.vitran.backend.service;

import com.vitran.backend.dto.PartyDto;
import com.vitran.backend.model.Party;
import com.vitran.backend.model.UserLogin;
import com.vitran.backend.model.enums.PartyStatus;
import com.vitran.backend.model.enums.PartyType;
import com.vitran.backend.repo.PartyRepo;
import com.vitran.backend.repo.UserLoginRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Service
public class PartyService {

    private final PartyRepo partyRepo;
    private final UserLoginRepo userLoginRepo;

    public PartyService(PartyRepo partyRepo, UserLoginRepo userLoginRepo) {
        this.partyRepo = partyRepo;
        this.userLoginRepo = userLoginRepo;
    }

    public ResponseEntity<?> findById(Long partyId) {
        return partyRepo.findById(partyId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> findAllParty() {
        return ResponseEntity.ok(partyRepo.findAll());
    }

    public ResponseEntity<?> create(PartyDto dto) {
        // Convert incoming String to Enum with validation
        PartyType type = parseEnum(dto.partyType(), PartyType.class, "partyType");
        PartyStatus status = dto.partyStatus() != null ? parseEnum(dto.partyStatus(), PartyStatus.class, "partyStatus") : PartyStatus.ACTIVE; // default

        Party party = new Party().setPartyType(type).setStatus(status)
                .setFromDate(dto.fromDate() != null ? dto.fromDate() : LocalDateTime.now())
                .setFirstName(dto.firstName()).setLastName(dto.lastName())
                .setGender(dto.gender()).setOrgName(dto.orgName()).setThruDate(dto.thruDate());

        Long savedId = partyRepo.save(party).getPartyId();
        return ResponseEntity.ok(Map.of("message", "Party created successfully", "partyId", savedId));
    }

    public ResponseEntity<?> patch(Long partyId, PartyDto dto) {
        Party party = partyRepo.findById(partyId).orElseThrow(() -> new NoSuchElementException("Party not found: " + partyId));

        updateIf(Objects::nonNull, dto::partyType, party::setPartyType, v -> parseEnum(v, PartyType.class, "partyType"));
        updateIf(Objects::nonNull, dto::partyStatus, party::setStatus, v -> parseEnum(v, PartyStatus.class, "partyStatus"));

        updateIf(Objects::nonNull, dto::fromDate, party::setFromDate, dto.fromDate());
        updateIf(Objects::nonNull, dto::thruDate, party::setThruDate, dto.thruDate());
        updateIf(Objects::nonNull, dto::firstName, party::setFirstName, dto.firstName());
        updateIf(Objects::nonNull, dto::lastName, party::setLastName, dto.lastName());
        updateIf(Objects::nonNull, dto::gender, party::setGender, dto.gender());
        updateIf(Objects::nonNull, dto::orgName, party::setOrgName, dto.orgName());

        Party updated = partyRepo.save(party);
        return ResponseEntity.ok(Map.of("message", "Party updated successfully", "partyId", updated.getPartyId()));
    }

    public ResponseEntity<?> delete(Long partyId) {
        Party party = partyRepo.findById(partyId).orElseThrow(() -> new NoSuchElementException("Party not found: " + partyId));
        party.setStatus(PartyStatus.INACTIVE);
        party.setThruDate(LocalDateTime.now());
        Optional<UserLogin> userLogin = userLoginRepo.findByParty(party);
        partyRepo.save(party);
        return ResponseEntity.ok(Map.of("message", "Party deleted successfully", "partyId", partyId));
    }

    // Helpers:

    /** Helper for partial field updates */
    private <T> void updateIf(Predicate<T> condition, Supplier<T> getter, Consumer<T> setter, T value) {
        if (condition.test(getter.get())) setter.accept(value);
    }

    /** Helper for partial field updates with type conversion */
    private <T, R> void updateIf(Predicate<T> condition, Supplier<T> getter, Consumer<R> setter, Function<T, R> mapper) {
        T value = getter.get();
        if (condition.test(value)) setter.accept(mapper.apply(value));
    }

    /** Generic safe parser for enums from strings */
    private <E extends Enum<E>> E parseEnum(String value, Class<E> enumType, String fieldName) {
        try {
            return Enum.valueOf(enumType, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("Invalid value for " + fieldName + ": " + value);
        }
    }
}
