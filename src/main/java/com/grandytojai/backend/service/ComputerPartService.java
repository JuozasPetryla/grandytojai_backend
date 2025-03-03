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

    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerPartsByType(String partType) {
        List<ComputerPartResponseDTO> responseDTOs = computerPartRepository.readComputerPartsByType(partType)
            .stream()
            .map(ComputerPartResponseDTO::of)
            .toList();

        return ResponseEntity.ok(responseDTOs);
    }

    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerParts() {
        List<ComputerPartResponseDTO> responseDTOs = computerPartRepository.readComputerParts()
            .stream()
            .map(ComputerPartResponseDTO::of)
            .toList();

        return ResponseEntity.ok(responseDTOs);
    }

    public ResponseEntity<ComputerPartResponseDTO> createComputerPart(ComputerPartRequestDTO computerPartRequestDTO) {

        Optional<ComputerPart> existingPart = computerPartRepository.readComputerPartByBarcode(computerPartRequestDTO.getBarcode());
        if (existingPart.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ComputerPartResponseDTO.of(existingPart.get()));
        }

        ComputerPart computerPart = new ComputerPart();
        computerPart.setBarcode(computerPartRequestDTO.getBarcode());
        computerPart.setPartName(computerPartRequestDTO.getPartName());
        computerPart.setPartType(computerPartRequestDTO.getPartType());
        computerPart.setPrice(computerPartRequestDTO.getPrice());
        computerPart.setImageUrl(computerPartRequestDTO.getImageUrl());

        computerPartRepository.createComputerPart(computerPart);

        return ResponseEntity.status(HttpStatus.CREATED).body(ComputerPartResponseDTO.of(computerPart));
    }


}
