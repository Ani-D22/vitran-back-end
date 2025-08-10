package com.vitran.backend.repo;

import com.vitran.backend.model.Enumeration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnumerationRepo extends JpaRepository<Enumeration, String> {
    Enumeration findByEnumTypeId(String enumTypeId);
    Enumeration findByEnumTypeIdAndenumId(String enumTypeId, String enumId);
}
