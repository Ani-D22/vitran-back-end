package com.vitran.backend.service;

import com.vitran.backend.dto.GenericMapper;
import com.vitran.backend.dto.PartyDto;
import com.vitran.backend.model.Enumeration;
import com.vitran.backend.model.Party;
import com.vitran.backend.repo.EnumerationRepo;
import com.vitran.backend.repo.PartyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PartyService {
    @Autowired
    private PartyRepo partyRepo;
    @Autowired
    private EnumerationRepo enumerationRepo;

    public ResponseEntity<?> findById(Long partyId) {
        return ResponseEntity.ok(partyRepo.findById(partyId).toString());
    }

    public ResponseEntity<?> findAllParty() {
        return ResponseEntity.ok(partyRepo.findAll());
    }

    public ResponseEntity<?> create(Party party) {
        Enumeration partyStatus = enumerationRepo.findByEnumTypeIdAndEnumId("PARTY_STATUS", "ACTIVE");
        party.setStatus(partyStatus);
        return ResponseEntity.ok(partyRepo.save(party).getPartyId());
    }

    public ResponseEntity<?> patch(Long partyId, PartyDto partyDto) {
        Party party = partyRepo.findById(partyId).orElseThrow(() -> new RuntimeException("Party not found"));
        GenericMapper.patchEntity(partyDto, party);
        return ResponseEntity.ok(partyRepo.save(party).getPartyId());
    }

    public ResponseEntity<?> delete(Long partyId) {

        if (partyRepo.existsById(partyId)) {
            PartyDto partyDto = new PartyDto();
            partyDto.setPartyId(partyId);
            Enumeration partyStatus = enumerationRepo.findByEnumTypeIdAndEnumId("PARTY_STATUS", "INACTIVE");
            partyDto.setStatus(partyStatus);
            partyDto.setThruDate(LocalDateTime.now());

            ResponseEntity<?> deletedPartyId = this.patch(partyId, partyDto);
            return ResponseEntity.ok(deletedPartyId);
        }

        return ResponseEntity.badRequest().build();
    }
}
