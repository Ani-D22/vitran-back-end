package com.vitran.backend.service;

import com.vitran.backend.dto.EnumerationDto;
import com.vitran.backend.model.Enumeration;
import com.vitran.backend.repo.EnumerationRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EnumerationService {

    private final EnumerationRepo enumerationRepo;
    private final Map<String, List<Enumeration>> enumCache = new HashMap<>();

    public EnumerationService(EnumerationRepo enumerationRepo) {
        this.enumerationRepo = enumerationRepo;
    }

    @PostConstruct
    private void loadEnumCache() {
        refreshCache();
    }

    private void refreshCache() {
        enumCache.clear();
        enumerationRepo.findAll()
                .forEach(e -> enumCache
                        .computeIfAbsent(e.getEnumTypeId(), k -> new ArrayList<>()).add(e));
    }

    /** Find enumeration by ID */
    public Optional<Enumeration> findById(String enumId) {
        return enumerationRepo.findById(enumId);
    }

    /** Create new enumeration from DTO */
    public Enumeration create(EnumerationDto dto) {
        Enumeration enumeration = new Enumeration().setEnumId(dto.enumId()).setEnumTypeId(dto.enumTypeId()).setDescription(dto.description());

        Enumeration saved = enumerationRepo.save(enumeration);
        refreshCache();
        return saved;
    }

    /** Update/Patch enumeration directly from DTO */
    public Enumeration update(String enumId, EnumerationDto dto) {
        Enumeration enumeration = enumerationRepo.findById(enumId).orElseThrow(() -> new NoSuchElementException("Enumeration not found: " + enumId));

        if (dto.enumTypeId() != null) enumeration.setEnumTypeId(dto.enumTypeId());
        if (dto.description() != null) enumeration.setDescription(dto.description());

        Enumeration updated = enumerationRepo.save(enumeration);
        refreshCache();
        return updated;
    }

    /** Delete enumeration by ID */
    public void delete(String enumId) {
        Enumeration existing = enumerationRepo.findById(enumId).orElseThrow(() -> new NoSuchElementException("Enumeration not found: " + enumId));

        enumerationRepo.delete(existing);
        refreshCache();
    }

    /** Get all enums for a specific type (cached) */
    public List<Enumeration> getEnumsByType(String typeId) {
        return enumCache.getOrDefault(typeId, Collections.emptyList());
    }

    /** Get single enum by type + id (cached) */
    public Optional<Enumeration> getEnum(String typeId, String enumId) {
        return getEnumsByType(typeId).stream().filter(e -> e.getEnumId().equalsIgnoreCase(enumId)).findFirst();
    }

    /** Helper: check if type+id is valid */
    public boolean isValidEnum(String typeId, String enumId) {
        return getEnum(typeId, enumId).isPresent();
    }
}
