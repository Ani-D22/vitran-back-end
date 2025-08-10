package com.vitran.backend.dto;

import com.vitran.backend.model.Enumeration;
import com.vitran.backend.model.Party;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyDto extends GenericDTO<Party> {
    private Long partyId;
    private Enumeration status;
    private LocalDateTime thruDate;
}
