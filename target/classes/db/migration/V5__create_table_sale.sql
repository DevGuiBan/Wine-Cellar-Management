CREATE TABLE IF NOT EXISTS sale (
                                    id SERIAL PRIMARY KEY,
                                    client_id UUID NOT NULL,
                                    sale_date DATE NOT NULL,
                                    total_price DECIMAL(10,2) NOT NULL,
                                    CONSTRAINT fk_sale_client FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE IF NOT EXISTS sale_product (
                                            sale_id BIGINT NOT NULL,
                                            product_id BIGINT NOT NULL,
                                            PRIMARY KEY (sale_id, product_id),
                                            CONSTRAINT fk_sale FOREIGN KEY (sale_id) REFERENCES sale(id) ON DELETE CASCADE,
                                            CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);
