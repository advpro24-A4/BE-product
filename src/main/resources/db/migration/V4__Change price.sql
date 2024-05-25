ALTER TABLE orders
    DROP COLUMN voucher_id;

ALTER TABLE cart_items
    DROP COLUMN price;

ALTER TABLE cart_items
    ADD price INTEGER NOT NULL;

ALTER TABLE order_items
    DROP COLUMN price;

ALTER TABLE order_items
    ADD price INTEGER NOT NULL;

ALTER TABLE payments
    DROP COLUMN total_price;

ALTER TABLE payments
    ADD total_price DOUBLE PRECISION NOT NULL;