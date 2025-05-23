package com.grandytojai.backend.api.controller;

import com.grandytojai.backend.BackendApplication;
import com.grandytojai.backend.api.dto.computerPartDTO.ComputerPartResponseDTO;
import com.grandytojai.backend.api.dto.computerPartDTO.ComputerPartRequestDTO;
import com.grandytojai.backend.service.ComputerPartService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

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

    @PutMapping(value = "/resetStatus")
    @ResponseStatus(HttpStatus.OK)
    public void resetScrapingStatus(@RequestParam(required = false) String storeName) {
        computerPartService.resetScrapingStatus(storeName);
    }

    @GetMapping(value = "/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> countUniqueComputerParts(@RequestParam(required = false) Optional<String> searchValue) {
        return computerPartService.countUniqueComputerParts(searchValue);
    }

    @GetMapping(value = "/countByType")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> countUniqueComputerPartsByType(@RequestParam(required = false) Optional<String> category) {
        return computerPartService.countUniqueComputerPartsByType(category);
    }
    
    @GetMapping(value = "/{partType}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerPartsByType(
            @PathVariable(value="partType") String partType,
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "name, price ASC") String filter
    ) {
        return computerPartService.readComputerPartsByType(partType, limit, page, filter);
    }

    @GetMapping(value = "/part")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerPartsByBarcode(@RequestParam String barcode) throws UnsupportedEncodingException{
        return computerPartService.readComputerPartsByBarcode(URLDecoder.decode(barcode, StandardCharsets.UTF_8));
    }

    @GetMapping(value = "/cheapest/part")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ComputerPartResponseDTO>> readCheapestComputerPartsByBarcode(@RequestParam String barcode) throws UnsupportedEncodingException{
        return computerPartService.readCheapestComputerPartsByBarcode(URLDecoder.decode(barcode, StandardCharsets.UTF_8));
    }

    @GetMapping(value = "/best-deals")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerPartsDeals(
        @RequestParam(defaultValue = "100") int limit, 
        @RequestParam(defaultValue = "1") int page, 
        @RequestParam(required = false) Optional<String> searchValue
    ) {
        return computerPartService.readComputerPartsDeals(limit, page, searchValue);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerParts(
        @RequestParam(defaultValue = "100") int limit, 
        @RequestParam(defaultValue = "1") int page, 
        @RequestParam(required = false) Optional<String> searchValue
    ) {
        return computerPartService.readComputerParts(limit, page, searchValue);
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

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ComputerPartResponseDTO>> readComputerPartsBySearchValue(
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String searchValue,
            @RequestParam(defaultValue = "name, price ASC") String filter
    ) {
        return computerPartService.readComputerPartsBySearchValue(limit, page, searchValue, filter);
    }
}
