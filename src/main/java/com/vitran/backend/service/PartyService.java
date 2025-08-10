package com.vitran.backend.service;

import com.vitran.backend.dto.PartyDto;
import com.vitran.backend.model.Enumeration;
import com.vitran.backend.model.Party;
import com.vitran.backend.repo.EnumerationRepo;
import com.vitran.backend.repo.PartyRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PartyService {
    @Autowired
    private PartyRepo partyRepo;
    @Autowired
    private EnumerationRepo enumerationRepo;

    public ResponseEntity<?> findById(Long partyId) {
        return ResponseEntity.ok(partyRepo.findById(partyId).toString());
    }

    public ResponseEntity<Party> create(Party party) {
        Enumeration partyStatus = enumerationRepo.findByEnumTypeIdAndenumId("PARTY_STATUS", "ACTIVE");
        party.setStatus(partyStatus);
        return ResponseEntity.ok(partyRepo.save(party));
    }

    public Party patch(PartyDto party) {
        return null;
    }

    public Boolean delete(Party party) {
        return null;
    }
}
