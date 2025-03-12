CREATE TABLE manager
(
    id       UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    cpf      VARCHAR(14)  NOT NULL UNIQUE,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);