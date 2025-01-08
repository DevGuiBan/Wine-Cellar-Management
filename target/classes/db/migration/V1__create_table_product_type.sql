create table if not exists product_type
(
    id   UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name varchar(100)
);