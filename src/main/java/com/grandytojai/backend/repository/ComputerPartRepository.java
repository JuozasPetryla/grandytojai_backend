package com.grandytojai.backend.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.grandytojai.backend.model.ComputerPart;

@Repository
@Mapper
public interface ComputerPartRepository {

    @Update("UPDATE computer_part SET seen_in_scrape = false WHERE store_name=#{storeName}")
    void resetScrapingStatus(String storeName);

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
                has_discount AS hasDiscount,
                seen_in_scrape AS seenInScrape
            FROM computer_part
            ORDER BY seen_in_scrape DESC, name ASC, price ASC
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
                has_discount AS hasDiscount,
                seen_in_scrape AS seenInScrape
            FROM computer_part
            WHERE
                UPPER(barcode) LIKE CONCAT('%', UPPER(#{searchValue}), '%')
                OR UPPER(name) LIKE CONCAT('%', UPPER(#{searchValue}), '%')
            ORDER BY seen_in_scrape DESC, name ASC, price ASC
            LIMIT #{limit} OFFSET #{offset}
            """)
    List<ComputerPart> readComputerPartsBySearchValue(int limit, int offset, String searchValue);

    @Select("""
    SELECT 
        cp.barcode, 
        cp.name AS partName, 
        cp.type AS partType, 
        cp.price, 
        cp.image_url AS imageUrl,
        cp.store_url AS storeUrl,
        cp.store_name AS storeName,
        cp.has_discount AS hasDiscount,
        cp.seen_in_scrape AS seenInScrape
    FROM computer_part cp
    INNER JOIN (
        SELECT barcode, MIN(price) AS min_price
        FROM computer_part
        WHERE type = #{partType}
        GROUP BY barcode
    ) grouped_parts
    ON cp.barcode = grouped_parts.barcode AND cp.price = grouped_parts.min_price
    WHERE cp.type = #{partType}
    ORDER BY cp.seen_in_scrape DESC, ${filter}
    LIMIT #{limit} OFFSET #{offset}
""")
    List<ComputerPart> readComputerPartsByType(String partType, int limit, int offset, String filter);


    @Select("""
        SELECT DISTINCT barcode, 
               name AS partName, 
               type AS partType, 
               price, 
               image_url AS imageUrl,
               store_url AS storeUrl,
               store_name AS storeName,
               has_discount AS hasDiscount,
               seen_in_scrape AS seenInScrape
        FROM computer_part
        WHERE has_discount = true AND
            (   
                UPPER(barcode) LIKE CONCAT('%', UPPER(#{searchValue}), '%')
                OR UPPER(name) LIKE CONCAT('%', UPPER(#{searchValue}), '%')
            )
        ORDER BY seen_in_scrape DESC, type ASC, price ASC
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
               has_discount AS hasDiscount,
               seen_in_scrape AS seenInScrape
        FROM computer_part
        WHERE has_discount = true
        ORDER BY seen_in_scrape DESC, type ASC, price ASC
        LIMIT #{limit} OFFSET #{offset}
        """)
    List<ComputerPart> readComputerPartsDeal(int limit, int offset);

    @Insert("""
            INSERT INTO computer_part (barcode, name, type, price, image_url, store_url, store_name, has_discount, seen_in_scrape)
            VALUES (#{barcode}, #{partName}, #{partType}, #{price}, #{imageUrl}, #{storeUrl}, #{storeName}, #{hasDiscount}, true)
            """)
    void createComputerPart(ComputerPart computerPart);

    @Select("SELECT * FROM computer_part WHERE barcode = #{barcode} AND store_name = #{storeName}")
    @Results({
        @Result(property = "partName", column = "name"),
        @Result(property = "partType", column = "type"),
        @Result(property = "imageUrl", column = "image_url"),
        @Result(property = "storeUrl", column = "store_url"),
        @Result(property = "storeName", column = "store_name"),
        @Result(property = "hasDiscount", column = "has_discount"),
        @Result(property = "seenInScrape", column = "seen_in_scrape")
    })
    Optional<ComputerPart> readComputerPartByBarcodeAndStore(String barcode, String storeName);

    @Update("""
        UPDATE computer_part SET
                name=#{partName},
                type=#{partType},
                price=#{price},
                image_url=#{imageUrl},
                store_url=#{storeUrl},
                has_discount=#{hasDiscount},
                seen_in_scrape=true
        WHERE barcode=#{barcode} and store_name=#{storeName} 
        """)
    void updateComputerPart(ComputerPart computerPart);

    @Select("""
    SELECT * FROM (
        SELECT 
            barcode, 
            name AS partName, 
            type AS partType, 
            price, 
            image_url AS imageUrl,
            store_url AS storeUrl,
            store_name AS storeName,
            has_discount AS hasDiscount,
            ROW_NUMBER() OVER(PARTITION BY barcode ORDER BY price ASC) AS row_number,
            seen_in_scrape AS seenInScrape 
        FROM computer_part 
        WHERE barcode IN ('${barcodes}') 
    ) ranked_parts
    WHERE row_number = 1
""")
    List<ComputerPart> readCheapestComputerPartByBarcode(String barcodes);

    @Select("""
            SELECT DISTINCT barcode,
                name AS partName,
                type AS partType,
                price,
                image_url AS imageUrl,
                store_url AS storeUrl,
                store_name AS storeName,
                has_discount AS hasDiscount,
                ROW_NUMBER() OVER(PARTITION BY barcode ORDER BY price) AS row_number,
                seen_in_scrape AS seenInScrape
            FROM computer_part WHERE barcode IN ('${barcode}')
            """)
    List<ComputerPart> readComputerPartByBarcode(String barcode);

    @Select("""
    SELECT 
        cp.barcode, 
        cp.name AS partName, 
        cp.type AS partType, 
        cp.price, 
        cp.image_url AS imageUrl,
        cp.store_url AS storeUrl,
        cp.store_name AS storeName,
        cp.has_discount AS hasDiscount,
        cp.seen_in_scrape AS seenInScrape
    FROM computer_part cp
    INNER JOIN (
        SELECT barcode, MIN(price) AS min_price
        FROM computer_part
        WHERE
            UPPER(barcode) LIKE CONCAT('%', UPPER(#{searchValue}), '%')
            OR UPPER(name) LIKE CONCAT('%', UPPER(#{searchValue}), '%')
        GROUP BY barcode
    ) grouped_parts
    ON cp.barcode = grouped_parts.barcode AND cp.price = grouped_parts.min_price
    ORDER BY cp.seen_in_scrape DESC, ${filter}
    LIMIT #{limit} OFFSET #{offset}
""")
    List<ComputerPart> readComputerPartsForSearchPage(int limit, int offset, String searchValue, String filter);
}
