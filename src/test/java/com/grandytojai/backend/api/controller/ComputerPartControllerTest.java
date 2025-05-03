package com.grandytojai.backend.api.controller;

import com.grandytojai.backend.api.dto.computerPartDTO.ComputerPartRequestDTO;
import com.grandytojai.backend.api.dto.computerPartDTO.ComputerPartResponseDTO;
import com.grandytojai.backend.service.ComputerPartService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ComputerPartController.class)
public class ComputerPartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComputerPartService computerPartService;

    @Test
    void testCountUniqueComputerParts() throws Exception {
        Mockito.when(computerPartService.countUniqueComputerParts(any())).thenReturn(ResponseEntity.ok(5));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/computerParts/count"))
               .andExpect(status().isOk())
               .andExpect(content().string("5"));
    }

    @Test
    void testCountUniqueComputerPartsByType() throws Exception {
        Mockito.when(computerPartService.countUniqueComputerPartsByType(any())).thenReturn(ResponseEntity.ok(3));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/computerParts/countByType"))
               .andExpect(status().isOk())
               .andExpect(content().string("3"));
    }

    @Test
    void testReadComputerPartsByType() throws Exception {
        Mockito.when(computerPartService.readComputerPartsByType(anyString(), anyInt(), anyInt()))
               .thenReturn(ResponseEntity.ok(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/computerParts/GPU")
                .param("limit", "10")
                .param("page", "1"))
               .andExpect(status().isOk());
    }

    @Test
    void testReadComputerPartsByBarcode() throws Exception {
        Mockito.when(computerPartService.readComputerPartsByBarcode(anyString()))
               .thenReturn(ResponseEntity.ok(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/computerParts/part")
                .param("barcode", "1234567890"))
               .andExpect(status().isOk());
    }

    @Test
    void testReadComputerPartsDeals() throws Exception {
        Mockito.when(computerPartService.readComputerPartsDeals(anyInt(), anyInt(), any()))
               .thenReturn(ResponseEntity.ok(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/computerParts/best-deals")
                .param("limit", "10")
                .param("page", "1"))
               .andExpect(status().isOk());
    }

    @Test
    void testReadComputerParts() throws Exception {
        Mockito.when(computerPartService.readComputerParts(anyInt(), anyInt(), any()))
               .thenReturn(ResponseEntity.ok(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/computerParts")
                .param("limit", "10")
                .param("page", "1"))
               .andExpect(status().isOk());
    }

    @Test
    void testCreateComputerPart() throws Exception {
        ComputerPartResponseDTO mockResponse = new ComputerPartResponseDTO();

        Mockito.when(computerPartService.createComputerPart(any()))
               .thenReturn(ResponseEntity.status(201).body(mockResponse));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/computerParts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
               .andExpect(status().isCreated());
    }

    @Test
    void testUpdateComputerPart() throws Exception {
        ComputerPartResponseDTO mockResponse = new ComputerPartResponseDTO();

        Mockito.when(computerPartService.updateComputerPart(any()))
               .thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/computerParts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
               .andExpect(status().isOk());
    }
}
