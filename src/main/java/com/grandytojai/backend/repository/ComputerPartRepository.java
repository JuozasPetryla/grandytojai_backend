package com.grandytojai.backend.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
                image_url AS imageUrl 
            FROM computer_part
            """)
    List<ComputerPart> readComputerParts();
    
    @Select("""
            SELECT barcode, 
                name AS partName, 
                type AS partType, 
                price, 
                image_url AS imageUrl  
            FROM computer_part
            WHERE type=#{partType}
            """)
    List<ComputerPart> readComputerPartsByType(String partType);

    @Insert("""
            INSERT INTO computer_part (barcode, name, type, price, image_url)
            VALUES (#{barcode}, #{partName}, #{partType}, #{price}, #{imageUrl})
            """)
    void createComputerPart(ComputerPart computerPart);
}
