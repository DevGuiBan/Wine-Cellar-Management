CREATE TABLE sale_product
(
    id          BIGSERIAL PRIMARY KEY,
    sale_id     UUID   NOT NULL,
    product_id  BIGINT NOT NULL,
    quantity    BIGINT NOT NULL,
    total_value NUMERIC NOT NULL,

    CONSTRAINT fk_sale FOREIGN KEY (sale_id) REFERENCES sale (id) ON DELETE CASCADE,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);
