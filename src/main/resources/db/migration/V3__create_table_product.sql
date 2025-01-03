create table if not exists product(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name varchar(250) not null ,
    quantity int not null ,
    id_product_type UUID not null,
    id_supplier UUID not null,
    price numeric,
    description TEXT not null,
    CONSTRAINT fk_product_product_type FOREIGN KEY (id_product_type) REFERENCES product_type (id),
    CONSTRAINT fk_product_supplier FOREIGN KEY (id_supplier) REFERENCES supplier (id)
);