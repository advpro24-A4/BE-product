ALTER TABLE payments
    DROP COLUMN total_price;

ALTER TABLE payments
    ADD total_price BIGINT NOT NULL;