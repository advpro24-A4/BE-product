ALTER TABLE cart
    ADD CONSTRAINT uc_cart_user UNIQUE (user_id);