CREATE TABLE computer_part (
    barcode VARCHAR(64) NOT NULL,
    name VARCHAR(255),
    type VARCHAR(64),
    price DOUBLE PRECISION,
    image_url VARCHAR(512),
    store_url VARCHAR(512),
    store_name VARCHAR(255) NOT NULL,
    has_discount BOOLEAN,
    PRIMARY KEY (barcode, store_name)
);