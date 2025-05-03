package com.grandytojai.backend.service;

import com.grandytojai.backend.api.dto.computerPartDTO.ComputerPartRequestDTO;
import com.grandytojai.backend.api.dto.computerPartDTO.ComputerPartResponseDTO;
import com.grandytojai.backend.model.ComputerPart;
import com.grandytojai.backend.repository.ComputerPartRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ComputerPartServiceTest {

    @Mock
    private ComputerPartRepository computerPartRepository;

    @InjectMocks
    private ComputerPartService computerPartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCountUniqueComputerParts_WithSearchValue() {
        when(computerPartRepository.countUniqueComputerPartsBySearchValue("RAM")).thenReturn(10);

        ResponseEntity<Integer> response = computerPartService.countUniqueComputerParts(Optional.of("RAM"));

        assertEquals(10, response.getBody());
    }

    @Test
    void testCountUniqueComputerParts_WithoutSearchValue() {
        when(computerPartRepository.countUniqueComputerParts()).thenReturn(5);

        ResponseEntity<Integer> response = computerPartService.countUniqueComputerParts(Optional.empty());

        assertEquals(5, response.getBody());
    }

    @Test
    void testReadComputerPartsByType() {
        when(computerPartRepository.readComputerPartsByType(anyString(), anyInt(), anyInt())).thenReturn(Collections.emptyList());

        ResponseEntity<List<ComputerPartResponseDTO>> response = computerPartService.readComputerPartsByType("CPU", 10, 1);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testReadComputerPartsByBarcode() {
        when(computerPartRepository.readComputerPartByBarcode("1234")).thenReturn(Collections.emptyList());

        ResponseEntity<List<ComputerPartResponseDTO>> response = computerPartService.readComputerPartsByBarcode("1234");

        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testCreateComputerPart_Conflict() {
        ComputerPart existing = new ComputerPart();
        when(computerPartRepository.readComputerPartByBarcodeAndStore(anyString(), anyString())).thenReturn(Optional.of(existing));

        ComputerPartRequestDTO request = new ComputerPartRequestDTO();
        request.setBarcode("123");
        request.setStoreName("ShopA");

        ResponseEntity<ComputerPartResponseDTO> response = computerPartService.createComputerPart(request);

        assertEquals(409, response.getStatusCode().value());
    }

    @Test
    void testCreateComputerPart_Created() {
        when(computerPartRepository.readComputerPartByBarcodeAndStore(anyString(), anyString())).thenReturn(Optional.empty());

        ComputerPartRequestDTO request = new ComputerPartRequestDTO();
        request.setBarcode("123");
        request.setPartName("RAM");
        request.setPartType("Memory");
        request.setPrice(50.0);
        request.setImageUrl("image.jpg");
        request.setStoreUrl("store.com");
        request.setStoreName("ShopA");
        request.setHasDiscount(false);

        ResponseEntity<ComputerPartResponseDTO> response = computerPartService.createComputerPart(request);

        assertEquals(201, response.getStatusCode().value());
        verify(computerPartRepository, times(1)).createComputerPart(any());
    }

    @Test
    void testUpdateComputerPart_NotFound() {
        when(computerPartRepository.readComputerPartByBarcodeAndStore(anyString(), anyString())).thenReturn(Optional.empty());

        ComputerPartRequestDTO request = new ComputerPartRequestDTO();
        request.setBarcode("456");
        request.setStoreName("StoreA");

        ResponseEntity<ComputerPartResponseDTO> response = computerPartService.updateComputerPart(request);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testUpdateComputerPart_OK() {
        when(computerPartRepository.readComputerPartByBarcodeAndStore(anyString(), anyString())).thenReturn(Optional.of(new ComputerPart()));

        ComputerPartRequestDTO request = new ComputerPartRequestDTO();
        request.setBarcode("456");
        request.setPartName("SSD");
        request.setPartType("Storage");
        request.setPrice(100.0);
        request.setImageUrl("image2.jpg");
        request.setStoreUrl("store2.com");
        request.setStoreName("StoreB");
        request.setHasDiscount(true);

        ResponseEntity<ComputerPartResponseDTO> response = computerPartService.updateComputerPart(request);

        assertEquals(200, response.getStatusCode().value());
        verify(computerPartRepository, times(1)).updateComputerPart(any());
    }
}
