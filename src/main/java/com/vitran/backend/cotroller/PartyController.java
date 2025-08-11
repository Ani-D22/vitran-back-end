package com.vitran.backend.cotroller;

import com.vitran.backend.dto.PartyDto;
import com.vitran.backend.model.Party;
import com.vitran.backend.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @GetMapping("/{partyId}")
    public ResponseEntity<?> findById(@PathVariable Long partyId){
        return partyService.findById(partyId);
    }

    @GetMapping
    public ResponseEntity<?> findAllParties() {
        return partyService.findAllParty();
    }

    @PostMapping
    public ResponseEntity<?> createParty(@RequestBody Party party) {
        return partyService.create(party);
    }

    @PutMapping("/{partyId}")
    public ResponseEntity<?> updateParty(@PathVariable Long partyId, @RequestBody PartyDto partyDto) {
        return partyService.patch(partyId, partyDto);
    }

    @DeleteMapping("/{partyId}")
    public ResponseEntity<?> deleteParty(@PathVariable Long partyId) {
        return ResponseEntity.ok(partyService.delete(partyId));
    }
}
