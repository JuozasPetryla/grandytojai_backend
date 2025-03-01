package com.grandytojai.backend.service;

import java.util.List;

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
}
