package com.grandytojai.backend.repository;

import com.grandytojai.backend.model.ComputerPart;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;


@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Sql(scripts = "/test-data/computer_parts.sql")
public class ComputerPartRepositoryTest {

    @Autowired
    private ComputerPartRepository computerPartRepository;

    @Autowired
    private DataSource dataSource;

    @Test
    void printDatabaseInfo() throws Exception {
        try (var conn = dataSource.getConnection()) {
            System.out.println("DB URL: " + conn.getMetaData().getURL());
            System.out.println("DB Product: " + conn.getMetaData().getDatabaseProductName());
        }
    }

    @Test
    void testCountUniqueComputerParts() {
        Integer count = computerPartRepository.countUniqueComputerParts();
        assertTrue(count >= 0);
    }

    @Test
    void testCountUniqueComputerPartsBySearchValue() {
        Integer count = computerPartRepository.countUniqueComputerPartsBySearchValue("RAM");
        assertNotNull(count);
    }

    @Test
    void testCountUniqueComputerPartsByType() {
        Integer count = computerPartRepository.countUniqueComputerPartsByType("CPU");
        assertNotNull(count);
    }

    @Test
    void testReadComputerParts() {
        List<ComputerPart> parts = computerPartRepository.readComputerParts(10, 0);
        assertNotNull(parts);
    }

    @Test
    void testReadComputerPartsBySearchValue() {
        List<ComputerPart> parts = computerPartRepository.readComputerPartsBySearchValue(10, 0, "GPU");
        assertNotNull(parts);
    }

    @Test
    void testReadComputerPartsByType() {
        List<ComputerPart> parts = computerPartRepository.readComputerPartsByType("GPU", 10, 0, "name");
        assertNotNull(parts);
    }

    @Test
    void testReadComputerPartsDeal() {
        List<ComputerPart> parts = computerPartRepository.readComputerPartsDeal(10, 0);
        assertNotNull(parts);
    }

    @Test
    void testReadComputerPartsDealBySearchValue() {
        List<ComputerPart> parts = computerPartRepository.readComputerPartsDealBySearchValue(10, 0, "SSD");
        assertNotNull(parts);
    }

    @Test
    void testCreateAndReadComputerPartByBarcodeAndStore() {
        ComputerPart part = new ComputerPart();
        part.setBarcode("12345678");
        part.setPartName("Test RAM");
        part.setPartType("Memory");
        part.setPrice(79.99);
        part.setImageUrl("test.png");
        part.setStoreUrl("http://store.test/item");
        part.setStoreName("TestStore");
        part.setHasDiscount(true);

        computerPartRepository.createComputerPart(part);

        Optional<ComputerPart> found = computerPartRepository.readComputerPartByBarcodeAndStore("12345678", "TestStore");
        assertTrue(found.isPresent());
        assertEquals("Test RAM", found.get().getPartName());
    }

    @Test
    void testUpdateComputerPart() {
        Optional<ComputerPart> original = computerPartRepository.readComputerPartByBarcodeAndStore("existing-barcode", "StoreA");
        if (original.isPresent()) {
            ComputerPart part = original.get();
            part.setPartName("Updated Name");
            computerPartRepository.updateComputerPart(part);

            Optional<ComputerPart> updated = computerPartRepository.readComputerPartByBarcodeAndStore("existing-barcode", "StoreA");
            assertEquals("Updated Name", updated.get().getPartName());
        } else {
            fail("Required test record not found. Ensure test data is present.");
        }
    }

    @Test
    void testReadComputerPartByBarcode() {
        List<ComputerPart> parts = computerPartRepository.readComputerPartByBarcode("existing-barcode");
        assertNotNull(parts);
    }
}
