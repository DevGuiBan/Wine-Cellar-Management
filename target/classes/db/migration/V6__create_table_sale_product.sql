create table sale_product(
    id_sale UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    id_product BIGINT,
    constraint fk_pk_id_sale foreign key (id_sale) references sale (id),
    constraint fk_pk_id_product foreign key (id_product) references product (id)
);