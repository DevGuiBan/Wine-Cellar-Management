create table if not exists client
(
    id           UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(15)  NOT NULL,
    address      VARCHAR(255) NOT NULL
);