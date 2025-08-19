package com.vitran.backend.controller;

import com.vitran.backend.dto.PartyDto;
import com.vitran.backend.service.PartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/party")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @GetMapping("/{partyId}")
    public ResponseEntity<?> findById(@PathVariable Long partyId) {
        return partyService.findById(partyId);
    }

    @GetMapping
    public ResponseEntity<?> findAllParties() {
        return partyService.findAllParty();
    }

    @PostMapping
    public ResponseEntity<?> createParty(@RequestBody PartyDto partyDto) {
        return partyService.create(partyDto);
    }

    @PutMapping("/{partyId}")
    public ResponseEntity<?> updateParty(@PathVariable Long partyId, @RequestBody PartyDto partyDto) {
        return partyService.patch(partyId, partyDto);
    }

    @DeleteMapping("/{partyId}")
    public ResponseEntity<?> deleteParty(@PathVariable Long partyId) {
        return partyService.delete(partyId);
    }
}
