ALTER TABLE payments
    ADD payment_method VARCHAR(255);

ALTER TABLE payments
    ALTER COLUMN payment_method SET NOT NULL;