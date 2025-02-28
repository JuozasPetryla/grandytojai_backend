package com.grandytojai.backend.api.controller;

import com.grandytojai.backend.BackendApplication;
import com.grandytojai.backend.api.dto.computerPartDTO.ComputerPartResponseDTO;
import com.grandytojai.backend.service.ComputerPartService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerParts() {
        return computerPartService.readComputerParts();
    }
}
