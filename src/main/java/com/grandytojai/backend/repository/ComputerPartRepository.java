package com.grandytojai.backend.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.grandytojai.backend.model.ComputerPart;

@Repository
@Mapper
public interface ComputerPartRepository {

    @Select("""
            SELECT 
                barcode, 
                name AS partName, 
                type AS partType, 
                price, 
                image_url AS imageUrl,
                store_url AS storeUrl,
                store_name AS storeName
            FROM computer_part LIMIT #{limit} OFFSET #{offset}
            """)
    List<ComputerPart> readComputerParts(int limit, int offset);
    
    @Select("""
            SELECT barcode, 
                name AS partName, 
                type AS partType, 
                price, 
                image_url AS imageUrl,
                store_url AS storeUrl,
                store_name AS storeName  
            FROM computer_part
            WHERE type=#{partType}
            """)
    List<ComputerPart> readComputerPartsByType(String partType);

    @Insert("""
            INSERT INTO computer_part (barcode, name, type, price, image_url, store_url, store_name)
            VALUES (#{barcode}, #{partName}, #{partType}, #{price}, #{imageUrl}, #{storeUrl}, #{storeName})
            """)
    void createComputerPart(ComputerPart computerPart);

    @Select("""
            SELECT * FROM computer_part WHERE barcode=#{barcode} and store_name=#{storeName}
           """)
    Optional<ComputerPart> readComputerPartByBarcodeAndStore(String barcode, String storeName);

    @Update("""
        UPDATE computer_part SET
                name=#{partName},
                type=#{partType},
                price=#{price},
                image_url=#{imageUrl}
                store_url=#{storeUrl}
        WHERE barcode=#{barcode} and store_name=#{storeName} 
        """)
    void updateComputerPart(ComputerPart computerPart);
}
