CREATE TYPE payment_method_enum AS ENUM (
    'CARTÃO_DE_CRÉDITO',
    'CARTÃO_DE_DÉBITO',
    'DINHEIRO',
    'TRANSFERÊNCIA_BANCÁRIA'
    );

CREATE TABLE sale
(
    id             UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    id_client      UUID                NOT NULL,
    id_product     BIGINT              NOT NULL,
    quantity       BIGINT              NOT NULL,
    total_value    NUMERIC,
    discount       NUMERIC,
    payment_method payment_method_enum NOT NULL,

    CONSTRAINT fk_id_client FOREIGN KEY (id_client) REFERENCES client (id) ON DELETE CASCADE,
    CONSTRAINT fk_id_product FOREIGN KEY (id_product) REFERENCES product (id) ON DELETE CASCADE
);
