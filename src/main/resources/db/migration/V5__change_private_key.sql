ALTER TABLE computer_part DROP CONSTRAINT computer_part_pkey CASCADE;
ALTER TABLE computer_part ADD PRIMARY KEY (barcode, store_name);