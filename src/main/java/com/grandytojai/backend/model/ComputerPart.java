package com.grandytojai.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ComputerPart {
    private String barcode;
    private String partName;
    private String partType;
    private Double price;
    private String imageUrl;
    private String storeUrl;
    private String storeName;
    private Boolean hasDiscount;
}
