ALTER TABLE orders
    ADD no_hp_penerima VARCHAR(255);

ALTER TABLE orders
    ALTER COLUMN no_hp_penerima SET NOT NULL;