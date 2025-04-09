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
            SELECT COUNT(DISTINCT barcode) 
            FROM computer_part
            """)
    Integer countUniqueComputerParts();

    @Select("""
            SELECT COUNT(DISTINCT barcode) 
            FROM computer_part
            WHERE
                UPPER(barcode) LIKE CONCAT('%', UPPER(#{searchValue}), '%')
                OR UPPER(name) LIKE CONCAT('%', UPPER(#{searchValue}), '%')
            """)
    Integer countUniqueComputerPartsBySearchValue(String searchValue);

    @Select("""
            SELECT COUNT(DISTINCT barcode)
            FROM computer_part
            WHERE UPPER(type) = UPPER(#{type})
            """)
    Integer countUniqueComputerPartsByType(String type);
    
    @Select("""
            SELECT 
                DISTINCT barcode, 
                name AS partName, 
                type AS partType, 
                price, 
                image_url AS imageUrl,
                store_url AS storeUrl,
                store_name AS storeName,
                has_discount AS hasDiscount
            FROM computer_part
            ORDER BY name, price
            LIMIT #{limit} OFFSET #{offset}
            """)
    List<ComputerPart> readComputerParts(int limit, int offset);

    @Select("""
            SELECT 
                DISTINCT barcode, 
                name AS partName, 
                type AS partType, 
                price, 
                image_url AS imageUrl,
                store_url AS storeUrl,
                store_name AS storeName,
                has_discount AS hasDiscount
            FROM computer_part
            WHERE
                UPPER(barcode) LIKE CONCAT('%', UPPER(#{searchValue}), '%')
                OR UPPER(name) LIKE CONCAT('%', UPPER(#{searchValue}), '%')
            ORDER BY name, price
            LIMIT #{limit} OFFSET #{offset}
            """)
    List<ComputerPart> readComputerPartsBySearchValue(int limit, int offset, String searchValue);
    
    @Select("""
            SELECT DISTINCT barcode, 
                name AS partName, 
                type AS partType, 
                price, 
                image_url AS imageUrl,
                store_url AS storeUrl,
                store_name AS storeName,
                has_discount AS hasDiscount
            FROM computer_part
            WHERE type=#{partType}
            ORDER BY name, price
            LIMIT #{limit} OFFSET #{offset}
            """)
    List<ComputerPart> readComputerPartsByType(String partType, int limit, int offset);

    @Select("""
        SELECT DISTINCT barcode, 
               name AS partName, 
               type AS partType, 
               price, 
               image_url AS imageUrl,
               store_url AS storeUrl,
               store_name AS storeName,
               has_discount AS hasDiscount
        FROM computer_part
        WHERE has_discount = true AND
            (   
                UPPER(barcode) LIKE CONCAT('%', UPPER(#{searchValue}), '%')
                OR UPPER(name) LIKE CONCAT('%', UPPER(#{searchValue}), '%')
            )
        ORDER BY partType, price
        LIMIT #{limit} OFFSET #{offset}
        """)
    List<ComputerPart> readComputerPartsDealBySearchValue(int limit, int offset, String searchValue);

    @Select("""
        SELECT DISTINCT barcode, 
               name AS partName, 
               type AS partType, 
               price, 
               image_url AS imageUrl,
               store_url AS storeUrl,
               store_name AS storeName,
               has_discount AS hasDiscount
        FROM computer_part
        WHERE has_discount = true
        ORDER BY partType, price
        LIMIT #{limit} OFFSET #{offset}
        """)
    List<ComputerPart> readComputerPartsDeal(int limit, int offset);

    @Insert("""
            INSERT INTO computer_part (barcode, name, type, price, image_url, store_url, store_name, has_discount)
            VALUES (#{barcode}, #{partName}, #{partType}, #{price}, #{imageUrl}, #{storeUrl}, #{storeName}, #{hasDiscount})
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
                has_discount=#{hasDiscount}
        WHERE barcode=#{barcode} and store_name=#{storeName} 
        """)
    void updateComputerPart(ComputerPart computerPart);

    @Select("""
            SELECT 
                barcode, 
               name AS partName, 
               type AS partType, 
               price, 
               image_url AS imageUrl,
               store_url AS storeUrl,
               store_name AS storeName,
               has_discount AS hasDiscount
            FROM computer_part WHERE barcode=#{barcode} ORDER BY price
            """)
    List<ComputerPart> readComputerPartByBarcode(String barcode);
}
