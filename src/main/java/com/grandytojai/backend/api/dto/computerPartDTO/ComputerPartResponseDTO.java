package com.grandytojai.backend.api.dto.computerPartDTO;

import com.grandytojai.backend.model.ComputerPart;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
public class ComputerPartResponseDTO {
    @NonNull
    private String barcode;
    @NonNull
    private String partName;
    @NonNull
    private String partType;
    @NonNull
    private Double price;
    private String imageUrl;
    private String storeUrl;
    private String storeName;
    private Boolean hasDiscount;
    private Boolean seenInScrape;

    public static ComputerPartResponseDTO of(ComputerPart computerPart) {
        return ComputerPartResponseDTO.builder()
            .barcode(computerPart.getBarcode())
            .partName(computerPart.getPartName())
            .partType(computerPart.getPartType())
            .price(computerPart.getPrice())
            .imageUrl(computerPart.getImageUrl())
            .storeUrl(computerPart.getStoreUrl())
            .storeName(computerPart.getStoreName())
            .hasDiscount(computerPart.getHasDiscount())
            .seenInScrape(computerPart.getSeenInScrape())
            .build();
    }
}
