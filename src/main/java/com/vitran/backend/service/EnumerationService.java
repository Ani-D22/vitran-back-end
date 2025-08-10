package com.vitran.backend.service;

import com.vitran.backend.dto.EnumerationDto;
import com.vitran.backend.dto.GenericMapper;
import com.vitran.backend.model.Enumeration;
import com.vitran.backend.repo.EnumerationRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EnumerationService {
    @Autowired
    private EnumerationRepo enumerationRepo;
    private final Map<String, List<Enumeration>> enumCache = new HashMap<>();

    public ResponseEntity<?> findById(String enumId) {
        return ResponseEntity.ok(enumerationRepo.findById(enumId).toString());
    }

    public ResponseEntity<?> create(Enumeration enumeration) {
        return ResponseEntity.ok(enumerationRepo.save(enumeration).getEnumId());
    }

    public ResponseEntity<?> patch(String enumId, EnumerationDto enumerationDto) {
        Enumeration enumeration = enumerationRepo.findById(enumId).orElseThrow(() -> new RuntimeException("Not found"));
        GenericMapper.patchEntity(enumerationDto, enumeration);
        return ResponseEntity.ok(enumerationRepo.save(enumeration));
    }

    public Boolean hardDelete(String enumId) {
        enumerationRepo.delete(enumerationRepo.findById(enumId).orElseThrow(() -> new RuntimeException("Enumeration not found")));
        return true;
    }

    // Caching of enumerations

    @PostConstruct
    public void loadEnumCache() {
        List<Enumeration> allEnums = enumerationRepo.findAll();
        allEnums.forEach(e -> enumCache
                .computeIfAbsent(e.getEnumTypeId(), k -> new ArrayList<>())
                .add(e));
    }

    public List<Enumeration> getEnumsByType(String typeId) {
        return enumCache.getOrDefault(typeId, Collections.emptyList());
    }

    public boolean isValidEnum(String typeId, String enumId) {
        return getEnumsByType(typeId).stream()
                .anyMatch(e -> e.getEnumId().equalsIgnoreCase(enumId));
    }
}
