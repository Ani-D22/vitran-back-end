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
public class EnumerationDto extends GenericDTO<Enumeration> {
    private String enumId;
    private String enumTypeId;
    private String enumValue;
    private String description;
}
