DELETE FROM computer_part;

INSERT INTO computer_part (barcode, name, type, price, image_url, store_url, store_name, has_discount) VALUES
('11111111', 'Intel Core i5', 'CPU', 199.99, 'intel_i5.png', 'http://store.com/intel-i5', 'StoreA', false),
('22222222', 'AMD Ryzen 5', 'CPU', 189.99, 'ryzen5.png', 'http://store.com/ryzen5', 'StoreB', true),
('33333333', 'Corsair Vengeance 16GB', 'Memory', 79.99, 'corsair16gb.png', 'http://store.com/corsair16gb', 'StoreA', false),
('44444444', 'Samsung 970 EVO 1TB', 'Storage', 129.99, 'samsung970evo.png', 'http://store.com/970evo', 'StoreB', true),
('55555555', 'NVIDIA RTX 3060', 'GPU', 349.99, 'rtx3060.png', 'http://store.com/rtx3060', 'StoreC', true),
('existing-barcode', 'Kingston SSD 240GB', 'Storage', 49.99, 'kingston240.png', 'http://store.com/kingston240', 'StoreA', false);


INSERT INTO computer_part (barcode, name, type, price, image_url, store_url, store_name, has_discount) VALUES
('55555555', 'NVIDIA RTX 3060 - Alt Shop', 'GPU', 359.99, 'rtx3060_alt.png', 'http://altstore.com/rtx3060', 'StoreD', false);
