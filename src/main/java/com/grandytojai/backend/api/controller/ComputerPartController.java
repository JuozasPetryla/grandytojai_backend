package com.grandytojai.backend.api.controller;

import com.grandytojai.backend.BackendApplication;
import com.grandytojai.backend.api.dto.computerPartDTO.ComputerPartResponseDTO;
import com.grandytojai.backend.api.dto.computerPartDTO.ComputerPartRequestDTO;
import com.grandytojai.backend.service.ComputerPartService;

import java.util.List;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ComputerPartController.PATH)
public class ComputerPartController {
    public static final String PATH = BackendApplication.PATH + "/computerParts";

    private final ComputerPartService computerPartService;

    @GetMapping(value = "/{partType}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerPartsByType(@PathVariable(value="partType") String partType) {
        return computerPartService.readComputerPartsByType(partType);
    }


    @GetMapping(value = "/best-deals")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerPartsDeals(@RequestParam(defaultValue = "100") int limit, @RequestParam(defaultValue = "1") int page) {
        return computerPartService.readComputerPartsDeals(limit, page);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerParts(@RequestParam(defaultValue = "100") int limit, @RequestParam(defaultValue = "1") int page) {
        return computerPartService.readComputerParts(limit, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ComputerPartResponseDTO> createComputerPart(@RequestBody ComputerPartRequestDTO computerPartRequestDTO) {
        return computerPartService.createComputerPart(computerPartRequestDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ComputerPartResponseDTO> updateComputerPart(@RequestBody ComputerPartRequestDTO computerPartRequestDTO) {
        return computerPartService.updateComputerPart(computerPartRequestDTO);
    }

}
