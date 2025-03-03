package com.grandytojai.backend.api.dto.computerPartDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter  
public class ComputerPartRequestDTO {
    private String barcode;  
    private String partName;    
    private String partType;  
    private Double price;    
    private String imageUrl;    
}
