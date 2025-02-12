ALTER TABLE product_type
    ADD COLUMN id_class_product BIGINT;
ALTER TABLE product_type
    ADD CONSTRAINT fk_id_class_product FOREIGN KEY (id_class_product) REFERENCES class_products (id);