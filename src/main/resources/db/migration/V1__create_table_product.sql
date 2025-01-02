create table if not exists product(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name varchar(100),
    quantity int,
    id_product_type int,
    id_supplier int,
    price numeric,
    description varchar(200)
);