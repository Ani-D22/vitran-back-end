package com.vitran.backend.dto;

import com.vitran.backend.model.Enumeration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyDto extends GenericDTO<Enumeration> {
    private Long partyId;
    private Enumeration status;
}
