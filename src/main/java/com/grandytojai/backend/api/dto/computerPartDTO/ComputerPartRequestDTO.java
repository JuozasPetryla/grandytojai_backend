package com.grandytojai.backend.api.dto.computerPartDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
public class ComputerPartRequestDTO {
    // Placeholder data for now, will be used for POST and PUT endpoints
    private String partType;
}
