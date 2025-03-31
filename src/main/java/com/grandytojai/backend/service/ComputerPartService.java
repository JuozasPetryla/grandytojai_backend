package com.grandytojai.backend.service;

import java.util.List;
import java.util.Optional;

import com.grandytojai.backend.api.dto.computerPartDTO.ComputerPartRequestDTO;
import com.grandytojai.backend.model.ComputerPart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grandytojai.backend.api.dto.computerPartDTO.ComputerPartResponseDTO;
import com.grandytojai.backend.repository.ComputerPartRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ComputerPartService {
    private final ComputerPartRepository computerPartRepository;

    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerPartsByBarcode(String barcode) {
        List<ComputerPartResponseDTO> responseDTOs = computerPartRepository.readComputerPartByBarcode(barcode)
            .stream()
            .map(ComputerPartResponseDTO::of)
            .toList();

        return ResponseEntity.ok(responseDTOs);
    }

    public ResponseEntity<Integer> countUniqueComputerParts(Optional<String> searchValue) {
        if (searchValue.isPresent()) {
            Integer count = computerPartRepository.countUniqueComputerPartsBySearchValue(searchValue.get());
            return ResponseEntity.ok(count);
        }
        Integer count = computerPartRepository.countUniqueComputerParts();
        return ResponseEntity.ok(count);
    }
    
    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerPartsByType(String partType, int limit, int page) {
        int offset = limit * (page - 1);
        List<ComputerPartResponseDTO> responseDTOs = computerPartRepository.readComputerPartsByType(partType, limit, offset)
            .stream()
            .map(ComputerPartResponseDTO::of)
            .toList();

        return ResponseEntity.ok(responseDTOs);
    }
    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerPartsDeals(int limit, int page, Optional<String> searchValue) {
        int offset = limit * (page - 1);

        if (searchValue.isPresent()) {
            List<ComputerPartResponseDTO> responseDTOs = computerPartRepository.readComputerPartsBySearchValue(limit, offset, searchValue.get())
            .stream()
            .map(ComputerPartResponseDTO::of)
            .toList();

            return ResponseEntity.ok(responseDTOs);
        }

        List<ComputerPartResponseDTO> responseDTOs = computerPartRepository.readComputerPartsDeal(limit, offset)
            .stream()
            .map(ComputerPartResponseDTO::of)
            .toList();

        return ResponseEntity.ok(responseDTOs);
    }

    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerParts(int limit, int page, Optional<String> searchValue) {
        int offset = limit * (page - 1);

        if (searchValue.isPresent()) {
            List<ComputerPartResponseDTO> responseDTOs = computerPartRepository.readComputerPartsBySearchValue(limit, offset, searchValue.get())
            .stream()
            .map(ComputerPartResponseDTO::of)
            .toList();

            return ResponseEntity.ok(responseDTOs);
        }

        List<ComputerPartResponseDTO> responseDTOs = computerPartRepository.readComputerParts(limit, offset)
            .stream()
            .map(ComputerPartResponseDTO::of)
            .toList();

        return ResponseEntity.ok(responseDTOs);
    }

    public ResponseEntity<ComputerPartResponseDTO> createComputerPart(ComputerPartRequestDTO computerPartRequestDTO) {

        Optional<ComputerPart> existingPart = computerPartRepository.readComputerPartByBarcodeAndStore(computerPartRequestDTO.getBarcode(), computerPartRequestDTO.getStoreName());
        if (existingPart.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ComputerPartResponseDTO.of(existingPart.get()));
        }

        ComputerPart computerPart = new ComputerPart();
        computerPart.setBarcode(computerPartRequestDTO.getBarcode());
        computerPart.setPartName(computerPartRequestDTO.getPartName());
        computerPart.setPartType(computerPartRequestDTO.getPartType());
        computerPart.setPrice(computerPartRequestDTO.getPrice());
        computerPart.setImageUrl(computerPartRequestDTO.getImageUrl());
        computerPart.setStoreUrl(computerPartRequestDTO.getStoreUrl());
        computerPart.setStoreName(computerPartRequestDTO.getStoreName());

        computerPartRepository.createComputerPart(computerPart);

        return ResponseEntity.status(HttpStatus.CREATED).body(ComputerPartResponseDTO.of(computerPart));
    }

    public ResponseEntity<ComputerPartResponseDTO> updateComputerPart(ComputerPartRequestDTO computerPartRequestDTO) {
        ComputerPart computerPart = ComputerPart.builder()
        .barcode(computerPartRequestDTO.getBarcode())
        .partName(computerPartRequestDTO.getPartName())
        .partType(computerPartRequestDTO.getPartType())
        .price(computerPartRequestDTO.getPrice())
        .imageUrl(computerPartRequestDTO.getImageUrl())
        .build();
        
        if (!computerPartRepository.readComputerPartByBarcodeAndStore(computerPart.getBarcode(), computerPart.getStoreName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ComputerPartResponseDTO.of(computerPart));
        }

        computerPartRepository.updateComputerPart(computerPart);

        return ResponseEntity.status(HttpStatus.OK).body(ComputerPartResponseDTO.of(computerPart));
    }
}
