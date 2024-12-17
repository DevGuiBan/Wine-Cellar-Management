create table product(
    id SERIAL PRIMARY KEY,
    name varchar(100),
    quantity int,
    id_product_type int,
    id_supplier int,
    description varchar(200)
);