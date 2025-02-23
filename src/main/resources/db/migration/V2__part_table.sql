CREATE TABLE app.computer_part
(
    barcode     TEXT    NOT NULL,
    name        TEXT    NOT NULL,
    type        TEXT    NOT NULL,
    price       INT     NOT NULL,
    image_url   TEXT    NOT NULL,
    PRIMARY KEY (barcode)
);